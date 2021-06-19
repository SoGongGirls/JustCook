# :fork_and_knife: Project Summary
### :tomato: 프로젝트 동기
- #### 일회용 쓰레기 문제
  - COVID-19, 1인 가구 증가로 인한 배달 음식 수요 증가
  - 일회용품 쓰레기 문제의 심각성이 대두   
  
  :arrow_forward: **"배달음식 대신 집에서 해 먹을 수 있는 요리 뭐 없을까?"**
- #### 기존 레시피 앱의 한계
  - 사용자의 냉장고 속 재료로 만들 수 없는 레시피를 추천하는 기존 앱
  - 재료 입력 시 수동으로 일일이 추가해야 함   
  
  :arrow_forward: **"냉장고 속 재료들을 가지고 간편하게 레시피를 찾을 수 없을까?"**
- #### 사용자 특성 기반 추천 필요
  - 비건과 알레르기 보유자를 위한 레시피 앱 부족
  - 다이어터를 위한 칼로리 기반 레시피 기능 필요   
  
  :arrow_forward: **"상황에 맞게 필요한 레시피를 바로바로 찾을 수 없을까?"**<br/><br/><br/>   
   
   
### :tomato: 프로젝트 소개   
**요리만해**는 사용자의 냉장고에 있는 식재료를 이용해 만들 수 있는 레시피를 추천해주는 서비스입니다.   
앱 이름인 요리만해에는 사용자에게 **더 이상 메뉴 고민은 하지 말고 요리만 하라**는 메시지를 담고 있습니다.<br/>

기존의 레시피 앱들과는 달리 **식재료 기반 레시피 추천**이 주된 목적이며 비건과 알레르기 보유자 맞춤 레시피도 제공합니다.   
식자재를 학습한 **YOLO 모델**을 통해 스마트폰 카메라로 냉장고 속 재료들을 인식하여 재료 목록에 넣습니다.<br/><br/><br/><br/>



# :fork_and_knife: Feature Introduction
<br/><br/><br/><br/>



# :fork_and_knife: Development Process
### :tomato: 영상 인식 모델 제작
<details>
  <summary>:x:darkflow:x:</summary>
<div markdown="1">
  
### :one: 학습을 위한 데이터셋 수집
구글 데이터 크롤링과 직접 찍은 동영상 프레임 단위로 저장, 픽사베이 등 여러 사이트에서 이미지를 모음.      
bread 596개, egg 788개, sausage 656개, tomato 814개, **총 2854개**의 데이터셋 확보.   
<br/>
  
  
### :two: Annotation
labelImg를 통해 이미지 각각에 대해 boundary-box를 지정해줌.  
   
:warning: **이미지 파일 이름에 특수문자가 들어가면 안된다!!!!**   
:warning: **xml 파일 안에 <path> 내 경로에 이미지 파일이 존재해야 한다.**   
<br/>


### :three: Darkflow 준비
가상환경 생성하여 다크플로우 설치해줘야 에러가 안뜸.   
   
:warning: `ModuleNotFoundError: No module named 'tensorflow.contrib'` 에러는 `conda install tensorflow=1.14`로 해결.   
<br/>
   

### :four: Training   
+ **Training**   
   
`python flow --model ./cfg/my-tiny-yolo.cfg --labels ./labels.txt --trainer adam --dataset ../data/dataset/ --annotation ../data/annotations/ --train --summary ./logs
 --batch 5 --epoch 100 --save 50 --keep 5 --lr 0.0001 --gpu 0.5 --load -1`      
     
:heavy_plus_sign: **--lr**은 learning rate를 의미함.   
:heavy_plus_sign: **--gpu**는 gpu 사용 여부를 의미함. gpu를 사용하면 학습 시간이 더 빨라짐.   
:heavy_plus_sign: **-–load**는 이전 학습 가중치를 이어서 학습하겠다는 옵션으로 -1은 마지막 save를 불러옴.     
   
:warning: 식재료 각각을 따로따로 학습시키는 것보다는 모든 학습 데이터를 한번에 학습 시키는 것이 학습 효과가 좋은 것 같음.   


  

  
  
</div>
</details>
