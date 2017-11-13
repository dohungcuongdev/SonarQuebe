//api url from backend server
//const api_url = "http://localhost:8080/SystemReport/api/";
const api_url = "http://localhost:8080/DemoSpringMVCHibernate/api/";

//declare angular app and inject ngRoute to use angular routing
var app = angular.module('sonarqube-report-app', ["ngRoute"]);


// daily report controller 
app.controller('dailyReportCtrl', function($scope, $http) {
	$http.get(api_url + "allprojects").then(function (response) {
		// json get from backend server .../api/allprojects
		$scope.dailyData = response.data;
	});
	
	$scope.updateSonarQube = function() {
		// insert or update sonarqube .../api/updateSonarQube
		swal ( "Notify" ,  "Getting api from SonarQube" );
		$http.post(api_url + "updateSonarQube").then(
		function (response) {
			swal ( "Congrats" ,  "Update successfully!" ,  "success" );
		}, 
		function (error) {
			swal ( "Oops" ,  "Update error! " + error.statusText ,  "error" );
		});	
	};
});

// chart controller 
app.controller('chartCtrl', function($scope, $http, $routeParams) {
	$scope.chart = $routeParams.chart;
	$scope.project_name = $routeParams.project_name;
	if($scope.chart === 'all') {
		// draw all chart 
		drawChartByJson('code_smell');
		drawChartByJson('test_case');
		drawChartByJson('coverage');
		drawChartByJson('technical_debt');
		drawChartByJson('vulnerability');
	} else {
		// draw one chart 
		drawChartByJson($scope.chart);
	};
	
	// draw chart by json getted from backend server
	function drawChartByJson(chart) {
		// json get from backend server .../api/{{chart}}/{{projectid}}
		$http.get(api_url + chart + "/" + $routeParams.projectid).then(function (response) {
			var data = response.data;
			if(chart == 'technical_debt') {
				computeDataByTechDebtTime(data, $routeParams.tech_debt_time);
			}
			drawChart(chart, data, $routeParams.tech_debt_time);
		});	
	}
});

// angular routing config
app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        templateUrl : "angular/template/daily-report.htm"
    })
    .when("/chart/:chart/:projectid/:project_name", {
        templateUrl : "angular/template/chart.htm"
    })
	.when("/chart/:chart/:projectid/:project_name/:tech_debt_time", {
        templateUrl : "angular/template/chart.htm"
    })
	.otherwise({ // 404 handle
        templateUrl : "angular/template/daily-report.htm"
    });
});


// declare underscoreless filter replace '_' into ' '
app.filter('underscoreless', function () {
  return function (input) {
      return input.replace(/_/g, ' ');
  };
});


// draw chart function
function drawChart(chart, data, tech_debt_time) {
	var chart = AmCharts.makeChart( "chart-" + chart, {
          "type": "serial",
          "theme": "light",
          "dataProvider": data,
          "valueAxes": [ {
            "gridColor": "#FFFFFF",
            "gridAlpha": 0.2,
            "dashLength": 0,
			"title": getChartTitle(chart, tech_debt_time)
          } ],
          "gridAboveGraphs": true,
          "startDuration": 1,
          "graphs": [ {
            "balloonText": "[[category]]: <b>[[value]]</b>",
            "fillAlphas": 0.8,
            "lineAlpha": 0.2,
            "type": "column",
            "valueField": "value"
          } ],
          "chartCursor": {
            "categoryBalloonEnabled": false,
            "cursorAlpha": 0,
            "zoomable": false
          },
          "categoryField": "date",
          "categoryAxis": {
            "gridPosition": "start",
            "gridAlpha": 0,
            "tickPosition": "start",
            "tickLength": 20
          },
          "export": {
            "enabled": true
          }
     });
}


// function compute technical debt by min/hour/day
function computeDataByTechDebtTime(data, tech_debt_time) {
	var dividend = 1;
	if(tech_debt_time == 'hour')
		dividend = 60;
	if(tech_debt_time == 'day')
		dividend = 60*8;
	for(var i = 0; i < data.length; i++) 
		data[i].value = data[i].value/dividend;
}


//function get title of chart 
function getChartTitle(chart, tech_debt_time) {
	switch(chart) {
		case 'code_smell':
			return 'Number of code smell in date of mounth';
		case 'test_case':
			return 'Number of test cases in date of mounth';
		case 'coverage':
			return 'Percent (%) of coverage in date of mounth';
		case 'technical_debt':
			return 'Total time (' + tech_debt_time + ') technical debt in date of mounth';
		case 'vulnerability':
			return 'Number of vulnerabilities in date of mounth';
	} 
}