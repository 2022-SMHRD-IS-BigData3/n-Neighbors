var chartDom = document.getElementById('userFwordCountBarChart');
var myUserFwordCountBarChart = echarts.init(chartDom, 'dark');
var option;

function fetchFwordCountData() {
	axios.get('/ageGroupFwordCount')
		.then(response => {
			var data = response.data;
			var processedData = data.map(item => ({
				name: item.ageGroup,
				value: item.fwordCount
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
						name: '비속어횟수',
						type: 'bar',
						data: processedData,
						label: {
							show: true,
							position: 'right',
							valueAnimation: true
						}
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