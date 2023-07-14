var myChart = echarts.init(document.getElementById("userCountLineChart"));

var option = {
	color: ['#80FFA5'],
	title: {
		// text: '시간대별 실시간 사용자 수'
	},
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			type: 'cross',
			label: {
				backgroundColor: 'white'
			}
		}
	},
	legend: {
		data: ['현재 사용자 수']
	},
	toolbox: {
		feature: {
			saveAsImage: {}
		}
	},
	grid: {
		left: '3%',
		right: '4%',
		bottom: '3%',
		containLabel: true
	},
	xAxis: [
		{
			type: 'category',
			boundaryGap: false,
			data: ['08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00']
		}
	],
	yAxis: [
		{
			type: 'value'
		}
	],
	series: [
		{
			name: '현재 사용자 수',
			type: 'line',
			stack: 'Total',
			smooth: true,
			lineStyle: {
				width: 0
			},
			showSymbol: false,
			areaStyle: {
				opacity: 0.8,
				color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
					{
						offset: 0,
						color: 'rgb(128, 255, 165)'
					},
					{
						offset: 1,
						color: 'rgb(1, 191, 236)'
					}
				])
			},
			emphasis: {
				focus: 'series'
			},
			data: [10, 20, 30, 25, 15, 40, 37]
		}
	]
};
myChart.setOption(option);