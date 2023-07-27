var chartDom = document.getElementById('userFwordCountBarChart');
var myUserFwordCountBarChart = echarts.init(chartDom, 'dark');
var option;

// 연령대별 색상을 매핑하는 객체
var ageGroupColors = {
   '10대미만':'RGB(73,146,255)',
   '10대': 'RGB(124,255,178)',
   '20대': 'RGB(253,221,96)',
   '30대': 'RGB(255,110,118)',
   '40대': 'RGB(5,192,145)',
   '50대': 'RGB(88,217,249)',
   '60대이상': 'RGB(255,138,69)'
};

function fetchFwordCountData() {
   axios.get('/ageGroupFwordCount')
      .then(response => {
         var data = response.data;
         var processedData = data.map(item => ({
            name: item.ageGroup,
            value: item.fwordCount,
            itemStyle: {
               color: ageGroupColors[item.ageGroup] // 해당 연령대에 맞는 색상을 지정
            }
         }));

         option = {
            title: {
               text: '연령대별 비속어 횟수',
               //subtext: '부제목',
               left: 'center'
            },
            xAxis: {
               max: 'dataMax'
            },
            yAxis: {
               type: 'category',
               data: processedData.map(item => item.name),
               inverse: true,
               animationDuration: 300,
               animationDurationUpdate: 300,
               max: 6 // only the largest 3 bars will be displayed
            },
            series: [
               {
                  realtimeSort: true,
                  //name: '비속어횟수',
                  type: 'bar',
                  data: processedData,
                  label: {
                     show: true,
                     position: 'right',
                     valueAnimation: true
                  },
                  // 연령대별로 색깔을 지정하기 위해 color 속성을 사용합니다.
                  color: ['#5470C6', '#91CC75', '#EE6666', '#73C0DE', '#3BA272']
               }
            ],
            legend: {
               orient: 'vertical',
               left: 'left',
               show: true
            },
            animationDuration: 0,
            animationDurationUpdate: 3000,
            animationEasing: 'linear',
            animationEasingUpdate: 'linear'
         };

         myUserFwordCountBarChart.setOption(option);
      })
      .catch(error => {
         console.error('Error fetching data:', error);
      });
}

fetchFwordCountData();
setTimeout(function () {
  fetchFwordCountData();
}, 0);
setInterval(fetchFwordCountData, 5000);