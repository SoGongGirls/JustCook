package com.example.justcook.env;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;

/** 이미지를 다루는 유틸리티 클래스입니다. */
public class ImageUtils {
    // 이 값은 2^18-1이며, RGB값을 범위 앞에 고정하는데 사용됩니다.
    // 8비트로 정규화됩니다.
    static final int kMaxChannelValue = 262143;

    @SuppressWarnings("unused")
    private static final Logger LOGGER = new Logger();

    /**
     * 주어진 YUV420SP 이미지의 할당된 크기를 바이트 단위로 계산하는 유틸리티 방법
     * dimensions.
     */
    public static int getYUVByteSize(final int width, final int height) {
        // 휘도 평면에 픽셀 당 1바이트가 필요합니다.
        final int ySize = width * height;

        // UV 평면이 2x2 블록에서 작동하므로 크기가 홀수인 치수는 반올림해야 합니다.
        // 각 2x2 블록은 U 및 V용으로 각각 하나씩 인코딩하는 데 2바이트가 필요합니다.
        final int uvSize = ((width + 1) / 2) * ((height + 1) / 2) * 2;

        return ySize + uvSize;
    }

    /**
     * 분석할 비트맵 객체를 디스크에 저장합니다.
     *
     * @param bitmap The bitmap to save.
     */
    public static void saveBitmap(final Bitmap bitmap) {
        saveBitmap(bitmap, "preview.png");
    }

    /**
     * 분석할 비트맵 객체를 디스크에 저장합니다.
     *
     * @param bitmap The bitmap to save.
     * @param filename The location to save the bitmap to.
     */
    public static void saveBitmap(final Bitmap bitmap, final String filename) {
        final String root =
                Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "tensorflow";
        LOGGER.i("Saving %dx%d bitmap to %s.", bitmap.getWidth(), bitmap.getHeight(), root);
        final File myDir = new File(root);

        if (!myDir.mkdirs()) {
            LOGGER.i("Make dir failed");
        }

        final String fname = filename;
        final File file = new File(myDir, fname);
        if (file.exists()) {
            file.delete();
        }
        try {
            final FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 99, out);
            out.flush();
            out.close();
        } catch (final Exception e) {
            LOGGER.e(e, "Exception!");
        }
    }

    public static void convertYUV420SPToARGB8888(byte[] input, int width, int height, int[] output) {
        final int frameSize = width * height;
        for (int j = 0, yp = 0; j < height; j++) {
            int uvp = frameSize + (j >> 1) * width;
            int u = 0;
            int v = 0;

            for (int i = 0; i < width; i++, yp++) {
                int y = 0xff & input[yp];
                if ((i & 1) == 0) {
                    v = 0xff & input[uvp++];
                    u = 0xff & input[uvp++];
                }

                output[yp] = YUV2RGB(y, u, v);
            }
        }
    }

    private static int YUV2RGB(int y, int u, int v) {
        // YUV 값을 조정하고 체크합니다.
        y = (y - 16) < 0 ? 0 : (y - 16);
        u -= 128;
        v -= 128;

        // 이것은 부동 소수점 등가값입니다. 우리는 정수로 변환을 해야합니다.
        // 일부 안드로이드 장치에는 하드웨어에 부동 소수점이 없기 때문입니다.
        // nR = (int)(1.164 * nY + 2.018 * nU);
        // nG = (int)(1.164 * nY - 0.813 * nV - 0.391 * nU);
        // nB = (int)(1.164 * nY + 1.596 * nV);
        int y1192 = 1192 * y;
        int r = (y1192 + 1634 * v);
        int g = (y1192 - 833 * v - 400 * u);
        int b = (y1192 + 2066 * u);

        // 경계 내에 포함되도록 RGB값 클리핑 [ 0 , kMaxChannelValue ]
        r = r > kMaxChannelValue ? kMaxChannelValue : (r < 0 ? 0 : r);
        g = g > kMaxChannelValue ? kMaxChannelValue : (g < 0 ? 0 : g);
        b = b > kMaxChannelValue ? kMaxChannelValue : (b < 0 ? 0 : b);

        return 0xff000000 | ((r << 6) & 0xff0000) | ((g >> 2) & 0xff00) | ((b >> 10) & 0xff);
    }

    public static void convertYUV420ToARGB8888(
            byte[] yData,
            byte[] uData,
            byte[] vData,
            int width,
            int height,
            int yRowStride,
            int uvRowStride,
            int uvPixelStride,
            int[] out) {
        int yp = 0;
        for (int j = 0; j < height; j++) {
            int pY = yRowStride * j;
            int pUV = uvRowStride * (j >> 1);

            for (int i = 0; i < width; i++) {
                int uv_offset = pUV + (i >> 1) * uvPixelStride;

                out[yp++] = YUV2RGB(0xff & yData[pY + i], 0xff & uData[uv_offset], 0xff & vData[uv_offset]);
            }
        }
    }

    /**
     * 한 기준 프레임에서 다른 프레임으로 변환한 변환 행렬을 리턴합니다.
     * 자르기(측면 비율을 유지하려는 경우) 및 회전을 처리합니다.
     *
     * @param srcWidth Width of source frame.
     * @param srcHeight Height of source frame.
     * @param dstWidth Width of destination frame.
     * @param dstHeight Height of destination frame.
     * @param applyRotation Amount of rotation to apply from one frame to another. Must be a multiple
     *     of 90.
     * @param maintainAspectRatio If true, will ensure that scaling in x and y remains constant,
     *     cropping the image if necessary.
     * @return The transformation fulfilling the desired requirements.
     */
    public static Matrix getTransformationMatrix(
            final int srcWidth,
            final int srcHeight,
            final int dstWidth,
            final int dstHeight,
            final int applyRotation,
            final boolean maintainAspectRatio) {
        final Matrix matrix = new Matrix();

        if (applyRotation != 0) {
            if (applyRotation % 90 != 0) {
                LOGGER.w("Rotation of %d % 90 != 0", applyRotation);
            }

            // 이미지의 중심이 원래 위치에 있도록 옮깁니다.
            matrix.postTranslate(-srcWidth / 2.0f, -srcHeight / 2.0f);

            // 원점을 회전합니다.
            matrix.postRotate(applyRotation);
        }

        // 이미 적용된 회전(있는 경우)을 설명하고 방법을 결정합니다.
        // 각 축에 대해 많은 배율이 필요합니다.
        final boolean transpose = (Math.abs(applyRotation) + 90) % 180 == 0;

        final int inWidth = transpose ? srcHeight : srcWidth;
        final int inHeight = transpose ? srcWidth : srcHeight;

        // 필요한 경우 배율을 적용합니다.
        if (inWidth != dstWidth || inHeight != dstHeight) {
            final float scaleFactorX = dstWidth / (float) inWidth;
            final float scaleFactorY = dstHeight / (float) inHeight;

            if (maintainAspectRatio) {
                // 최소 인자에 따라 확장하여 dst가 완전히 채워지도록 합니다.
                // 가로 세로 비율을 유지합니다. 일부 이미지가 가장자리에서 떨어질 수도 있습니다.
                final float scaleFactor = Math.max(scaleFactorX, scaleFactorY);
                matrix.postScale(scaleFactor, scaleFactor);
            } else {
                // src에서 dst를 채우도록 정확히 확장합니다.
                matrix.postScale(scaleFactorX, scaleFactorY);
            }
        }

        if (applyRotation != 0) {
            // 원본 중심 레퍼런스에서 대상 프레임으로 다시 변환합니다.
            matrix.postTranslate(dstWidth / 2.0f, dstHeight / 2.0f);
        }

        return matrix;
    }
}