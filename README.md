# Firebase
FirebaseTest는 실시간 데이터 베이스 예제구
FirestoreTest는 Firestore 데이터 베이스 예제에여

ui, ux 구리니까 참고....

아래는 firestore 데이터 베이스 구조 썼던 캡처 화면이구
![firestore 데이터 구조](https://github.com/NohAnswer/Firebase/assets/142373110/cd6b3cfe-8b2f-4496-9a25-aba58eb80e0a)
![firestore 데이터 구조2](https://github.com/NohAnswer/Firebase/assets/142373110/ebb1f2d6-daea-4f80-a9df-3d00bf0ea418)


아래는 실시간데이터 베이스 json 트리에여

{
  "postings": {
    "testpost1": {
      "body": "한우 세일 하길래 샀는데 싸게 잘 사지 않았나요?",
      "category": "식비",
      "ispublic": true,
      "likesorvotepay": 20,
      "paiddate": "2023.08.23",
      "postingdate": "2023.08.22",
      "postingtype": "pay",
      "price": 30000,
      "productortitle": "한우",
      "user": "testuser"
    },
    "tipstestpost": {
      "body": "적금상품이 나왔으니 주목해주세요",
      "category": "금융팁",
      "likesorvotepay": 15,
      "postingdate": "2023.08.22",
      "postingtype": "tips",
      "productortitle": "적금상품홍보",
      "user": "testuser"
    },
    "votetestpost": {
      "body": "노트북을 사고 싶은데 아낄까요 살까요?",
      "category": "온라인쇼핑",
      "ispaid": true,
      "ispublic": true,
      "likesorvotepay": 20,
      "paiddate": "2023.08.21",
      "postingdate": "2023.08.03",
      "postingtype": "vote",
      "price": 600000,
      "productortitle": "노트북",
      "user": "testuser",
      "voteduration": 7,
      "votesave": 30
    }
  },
  "user_list": {
    "testuser": {
      "email": "kjh@gmail.com",
      "id": "testuser",
      "name": "테스트1",
      "password": "1234"
    }
  }
}



모두들 화이또
