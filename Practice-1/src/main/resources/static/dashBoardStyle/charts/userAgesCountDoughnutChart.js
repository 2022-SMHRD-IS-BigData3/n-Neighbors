var chartDom = document.getElementById('userAgesCountDoughnutChart');
var myUserAgesCountDoughnutChart = echarts.init(chartDom, 'dark');

// 서버에서 데이터를 가져와서 도넛 차트를 업데이트하는 함수
function fetchUserAgesData() {
	axios.get('/userAgesCount')
		.then(response => {
			var data = response.data;

			// 도넛 차트에 사용될 데이터 형식으로 가공
			var processedData = data.map(item => ({
				name: item.ageGroup, // 연령대를 name으로 설정
				value: item.userCount // 사용자 횟수를 value로 설정
			}));

			// 도넛 차트 옵션 설정
			var option = {
				title: {
					text: '연령대별 사용자 횟수',
					//subtext: '부제목',
					left: 'center'
				},
				tooltip: {
					trigger: 'item'
				},
				legend: {
					orient: 'vertical',
					left: 'left'
				},
				series: [
					{
						name: '연령대별 사용자 횟수',
						type: 'pie',
						radius: '50%',
						data: processedData,
						emphasis: {
							itemStyle: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: 'rgba(0, 0, 0, 0.5)'
							}
						}
					}
				]
			};

			// 도넛 차트에 옵션 설정을 업데이트
			myUserAgesCountDoughnutChart.setOption(option);
		})
		.catch(error => {
			console.error('Error fetching data:', error);
		});
}

// 페이지 로드 시 데이터 가져오기
fetchUserAgesData();

// 5초마다 도넛 차트 업데이트하기
setInterval(fetchUserAgesData, 5000);