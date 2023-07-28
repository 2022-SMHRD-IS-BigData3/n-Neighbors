var app = {};
//var sock = new WebSockJS("/ws/chat")
//var ws = Stomp.over(sock);
//var reconnect = 0;
var chartDom = document.getElementById('userCountLineChart');
var myUserCountLineChart = echarts.init(chartDom, 'dark');

var option;
// 시간 표시 하는 함수.
// IIF(즉시 호출 함수식)
const categories = (function () {
  let now = new Date();
  let res = [];
  let len = 5;
  while (len--) {
	//현재 날짜와 시간으로 변수 'now'를 초기화
    res.unshift(now.toLocaleTimeString().replace(/^\D*/, ''));
    now = new Date(+now - 1000);
  }
  return res;
})();

const categories2 = (function () {
  let res = [];
  let len = 10;
  while (len--) {
    res.push(10 - len - 1);
  }
  return res;
})();
//선 차트의 초기 데이터 생성
const data = (function () {
  let res = [];
  let len = 10;
  while (len--) {
    res.push(Math.round(Math.random() * 100));
  }
  return res;
})();
const data2 = (function () {
  let res = [];
  let len = 0;
  while (len < 10) {
    res.push(+(Math.random() * 10 +5).toFixed(1));
    len++;
  }
  return res;
})();
//선 차트에 대한 옵션 정의
option = {
  title: {
    text: '실시간 비속어 탐지 횟수'
  },
  tooltip: {
    trigger: 'axis',
    axisPointer: {//데이터 포인트위에 마우스 올렸을 때 나타나는 선, 십자선 설정
      type: 'cross',
      label: {
        backgroundColor: '#283b56'
      }
    }
  },
  legend: {},// 차트의 범례 구성
  toolbox: {// 차트 도구 상자
    show: true,// 도구상자 표시 여부
    feature: {// 도구 상자에서 사용가능한 기능을 구성
	// 사용자가 테이블에서 데이터를 볼수 있는 데이터 보기 기능 제공
      dataView: { readOnly: false },// 편집 가능 여부
      restore: {},// 차트를 원래 상태로 복원하는 기능 추가
      saveAsImage: {}// 차트를 이미지로 저장하는 기능 추가
    }
  },
  dataZoom: {// 차트의 확대/축소 및 스크롤 기능
    show: false,// zoom 기능 비활성화
    start: 0,// zoom기능 시작 위치
    end: 100// zoom기능 종료 위치
  },
  xAxis: [// 차트의 x 축
    {
      type: 'category',// x축이 카테고리 축임을 지정
      boundaryGap: true,// 데이터가 x축의 두 범주 가운데에서 시작
      data: categories// categories 배열을 사용하여 첫 번째 축에 대한 데이터를 설정
    },
    {
      type: 'category',
      boundaryGap: true,
      data: categories2
    }
  ],
  yAxis: [	// 차트의 y축
    {
      type: 'value',// y축이 값을 나타내는 축임을 지정 
      scale: true,	// y축이 연속 스케일을 가지도록 설정
      name: '탐지횟수',
      max: 100,		// y축 최대값
      min: 0,		// y축 최소값
      boundaryGap: [0.2, 0.2]
    },
    {
      type: 'value',
      scale: true,
      name: '탐지횟수',
      max: 100,
      min: 0,
      boundaryGap: [0.2, 0.2]
    }
  ],
//선 차트에 대한 데이터
  series: [
  
    {
      //name: 'Dynamic Line',
      type: 'line',//선 차트 유형
	  areaStyle: {}, //선 차트의 영역 스타일
      data: data,	//선 차트의 초기 데이터
      itemStyle : { normal : { color:'#0984e3', lineStyle:{ color:'#0984e3' }}} // 3ba272 초록색
    }
  ]
};
// 카운트 변수의 초기 값 설정
app.count = 5;
// 라인 차트를 새 데이터로 업데이트하는 간격 설정
setInterval(function () {
  let axisData = new Date().toLocaleTimeString().replace(/^\D*/, '');
  // 첫 번째 요소를 이동하고 
  data.shift();
  //데이터 배열에 새 랜덤 값을 추가
  data.push(Math.round(Math.random() * 100));
  data2.shift();
  data2.push(+(Math.random() * 10 + 5).toFixed(1));
  categories.shift();
  categories.push(axisData);
  categories2.shift();
  categories2.push(app.count++);
// 새 데이터로 차트 업데이트
  myUserCountLineChart.setOption({
    xAxis: [
      {
        data: categories
      },
      {
        data: categories2
      }
    ],
    series: [
      {
        data: data
      },
      {
        data: data2
      }
    ]
  });
}, 2100);
// 차트에 초기 옵션 적용
option && myUserCountLineChart.setOption(option);