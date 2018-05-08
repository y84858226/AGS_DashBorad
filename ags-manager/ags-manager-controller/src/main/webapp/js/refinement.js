var previousPoint = null, previousLabel = null;
$.fn.UseTooltip = function () {	
    $(this).bind("plothover", function (event, pos, item) {
        if (item) {
            if ((previousLabel != item.series.label) || (previousPoint != item.dataIndex)) {
                previousPoint = item.dataIndex;
                previousLabel = item.series.label;
                $("#tooltip").remove();

                var x = item.datapoint[0];
                var y = item.datapoint[1];
                var color = item.series.color;
//                console.log(item.series.xaxis.ticks[x].label);                
                showTooltip(item.pageX,
                item.pageY,
                color,
                item.series.xaxis.ticks[x].label + " : <strong>" + y + "</strong> %");
            }
        } else {
            $("#tooltip").remove();
            previousPoint = null;
        }
    });
};

function showTooltip(x, y, color, contents) {
    $('<div id="tooltip">' + contents + '</div>').css({
        position: 'absolute',
        display: 'none',
        top: y - 40,
        left: x - 120,
        border: '2px solid ' + color,
        padding: '3px',
        'z-index':'50',
        'font-size': '9px',
        'border-radius': '5px',
        'background-color': '#fff',
        'font-family': 'Verdana, Arial, Helvetica, Tahoma, sans-serif',
        opacity: 0.9
    }).appendTo("body").fadeIn(200);
}

function createPLChart(result,type){
	var data=new Array();
	var all=new Array();
	var ticks=new Array();
	if(type=="week"){
		var number=0;
		data[number]=new Array();
		data[number][0]=number;
		data[number][1]=null;
		ticks[number]=new Array();
		ticks[number][0]=number;
		ticks[number][1]="";
		number=number+1;
		$.each(result,function(index,value){
			var week=value.week;
			var coverage=value.coverageAssignments;
			data[number]=new Array();
			data[number][0]=number;
			data[number][1]=coverage;
			ticks[number]=new Array();
			ticks[number][0]=number;
			ticks[number][1]="week"+week;
			number=number+1;
			
			data[number]=new Array();
			data[number][0]=number;
			data[number][1]=null;
			ticks[number]=new Array();
			ticks[number][0]=number;
			ticks[number][1]="";
			number=number+1;
		})
	}else if(type=="pl"){
		var number=0;
		var array=$("#PL").selectpicker('val');
		
		data[number]=new Array();
		data[number][0]=number;
		data[number][1]=null;
		ticks[number]=new Array();
		ticks[number][0]=number;
		ticks[number][1]="";
		number=number+1;
		
		for (k in array) {
			$.each(result,function(index,value){
				var pl=value.pl;
				var week=value.week;
				var coverage=value.coverageAssignments;
				if(array[k]==pl){
					data[number]=new Array();
					data[number][0]=number;
					data[number][1]=coverage;
					ticks[number]=new Array();
					ticks[number][0]=number;
					ticks[number][1]="week"+week+":"+pl;
					number=number+1;
				}
			})
			data[number]=new Array();
			data[number][0]=number;
			data[number][1]=null;
			ticks[number]=new Array();
			ticks[number][0]=number;
			ticks[number][1]="";
			number=number+1;
		}
	}else if(type=="gl"){
		var number=0;
		var array=$("#GL").selectpicker('val');
		
		data[number]=new Array();
		data[number][0]=number;
		data[number][1]=null;
		ticks[number]=new Array();
		ticks[number][0]=number;
		ticks[number][1]="";
		number=number+1;
		
		for (k in array) {
			$.each(result,function(index,value){
				var gl=value.gl;
				var week=value.week;
				var coverage=value.coverageAssignments;
				if(array[k]==gl){
					data[number]=new Array();
					data[number][0]=number;
					data[number][1]=coverage;
					ticks[number]=new Array();
					ticks[number][0]=number;
					ticks[number][1]="week"+week+":"+gl;
					number=number+1;
				}
			})
			data[number]=new Array();
			data[number][0]=number;
			data[number][1]=null;
			ticks[number]=new Array();
			ticks[number][0]=number;
			ticks[number][1]="";
			number=number+1;
		}
	}
	
	var dataset = [{ data: data, color:"#5482FF"}];
	var options = {
		    series: {
		        bars: {
		            show: true
		        }
		    },
		    bars: {
		        align: "center",
		        barWidth: 1,
		        numbers:{
                    show:function(num){return num+"%";}
                }
		    },
		    xaxis: {
		        axisLabel: "name",
		        axisLabelUseCanvas: true,
		        axisLabelFontSizePixels: 12,
		        axisLabelFontFamily: 'Verdana, Arial',
		        axisLabelPadding: 10,
		        ticks: ticks
		    },
		    yaxis: {
		        axisLabel: "Coverage",
		        axisLabelUseCanvas: true,
		        axisLabelFontSizePixels: 12,
		        axisLabelFontFamily: 'Verdana, Arial',
		        axisLabelPadding: 3
		    },
		    legend: {
		        noColumns: 0,
		        labelBoxBorderColor: "#000000",
		        position: "nw"
		    },
		    grid: {
		        hoverable: true,
		        borderWidth: 1,
		        backgroundColor: { colors: ["#ffffff", "#EDF5FF"] }
		    }
		};
	$.plot($("#CoverageChart"), dataset, options);
	$("#CoverageChart").UseTooltip();
	$(".xAxis > .tickLabel").rotate(+40);
}

function getBrowseNodeCoverageData(){
	var week=$("#Week").selectpicker('val');
	var pl=$("#PL").selectpicker('val');
	var gl=$("#GL").selectpicker('val');
	var band=$("#Band").selectpicker('val');
	
	if(week!=null){
		if(pl!=null){
			if(gl!=null){
				//显示gl数据
				$.ajax({
					url : "findRefinementBandCoverage.spring",
					type : "post",
					data:{
						sourceCountry:$("#SourceCountry").val(),
						targetCountry:$("#TargetCountry").val(),
						year:$("#Year").val(),
						weeks:$("#Week").selectpicker('val'),
						gls:$("#GL").selectpicker('val'),
						bands:$("#Band").selectpicker('val')
					},
					traditional:true,
					dataType : "json",
					success : function(result) {
						createPLChart(result,'gl');
					}
				})
			}else{
				//显示PL
				$.ajax({
					url : "findRefinementPlCoverage.spring",
					type : "post",
					data:{
						sourceCountry:$("#SourceCountry").val(),
						targetCountry:$("#TargetCountry").val(),
						year:$("#Year").val(),
						weeks:$("#Week").selectpicker('val'),
						pls:$("#PL").selectpicker('val')
					},
					traditional:true,
					dataType : "json",
					success : function(result) {
						createPLChart(result,'pl');
					}
				})
			}
		}else{
			//显示全部week的全部数据
			$.ajax({
				url : "findRefinementWeekCoverage.spring",
				type : "post",
				data:{
					sourceCountry:$("#SourceCountry").val(),
					targetCountry:$("#TargetCountry").val(),
					year:$("#Year").val(),
					weeks:$("#Week").selectpicker('val')
				},
				traditional:true,
				dataType : "json",
				success : function(result) {
					createPLChart(result,'week');
				}
			})
		}
	}else{
		//显示空表
		$("#CoverageChart").html("");
	}
}

function loadSourceCountry(){
	$.ajax({
		url : "findRefinementSourceCountry.spring",
		type : "post",
		dataType : "json",
		success : function(data) {
			var html="";
			$.each(data,function(k,v){
				html+='<option value="'+v.sourceCountry+'">'+v.sourceCountry+'</option>';
			})
			
			$("#SourceCountry").html(html);
			
			loadTargetCountry();
			
			$('.selectpicker').selectpicker('refresh');
		}
	})
}

function loadTargetCountry(){
	$.ajax({
		url : "findRefinementTargetCountry.spring",
		type : "post",
		dataType : "json",
		data:{
			sourceCountry:$("#SourceCountry").val()
		},
		success : function(data) {
			var html="";
			$.each(data,function(k,v){
				html+='<option value="'+v.targetCountry+'">'+v.targetCountry+'</option>';
			})
			$("#TargetCountry").html(html);
			
			//加载Year
			loadYear();
			$('.selectpicker').selectpicker('refresh');
		}
	})
}
function loadYear(){
	$.ajax({
		url : "findRefinementYear.spring",
		type : "post",
		dataType : "json",
		data:{
			sourceCountry:$("#SourceCountry").val(),
			targetCountry:$("#TargetCountry").val()
		},
		success : function(data) {
			var html="";
			$.each(data,function(k,v){
				html+='<option value="'+v.year+'">'+v.year+'</option>';
			})
			$("#Year").html(html);
			
			//加载Week
			loadWeek();
			$('.selectpicker').selectpicker('refresh');
		}
	})
}



function loadWeek(){
	$.ajax({
		url : "findRefinementWeek.spring",
		type : "post",
		dataType : "json",
		data:{
			sourceCountry:$("#SourceCountry").val(),
			targetCountry:$("#TargetCountry").val(),
			year:$("#Year").val()
		},
		success : function(data) {
			var html="";
			$.each(data,function(k,v){
				html+='<option value="'+v.week+'">'+v.week+'</option>';
			})
			$("#Week").html(html);
			$("#PL").html("");
			$("#GL").html("");
			$("#Band").html("");
			$('.selectpicker').selectpicker('refresh');
			getBrowseNodeCoverageData();
		}
	})
}

function loadPL(){
	$.ajax({
		url : "findRefinementPL.spring",
		type : "post",
		dataType : "json",
		data:{
			sourceCountry:$("#SourceCountry").val(),
			targetCountry:$("#TargetCountry").val(),
			year:$("#Year").val(),
			weeks:$("#Week").selectpicker('val')
		},
		traditional:true,
		success : function(data) {
			var html="";
			$.each(data,function(k,v){
				html+='<option value="'+v.pl+'">'+v.pl+'</option>';
			})
			$("#PL").html(html);
			$("#GL").html("");
			$("#Band").html("");
			$('.selectpicker').selectpicker('refresh');
			getBrowseNodeCoverageData();
		}
	})
}

function loadGL(){
	$.ajax({
		url : "findRefinementGL.spring",
		type : "post",
		dataType : "json",
		data:{
			sourceCountry:$("#SourceCountry").val(),
			targetCountry:$("#TargetCountry").val(),
			year:$("#Year").val(),
			weeks:$("#Week").selectpicker('val'),
			pls:$("#PL").selectpicker('val')
		},
		traditional:true,
		success : function(data) {
			var html="";
			var array=$("#PL").selectpicker('val');
			$.each(array,function(n1,v1){
				html+='<optgroup label="'+v1+'">';
				$.each(data,function(n2,v2){
					if(v1==v2.pl){
						html+='<option value="'+v2.gl+'">'+v2.gl+'</option>';
					}
				})
				html+='</optgroup>';
			})
			$("#GL").html(html);
			$("#Band").html("");
			$('.selectpicker').selectpicker('refresh');
			getBrowseNodeCoverageData();
		}
	})

}

function loadBand(){
	$.ajax({
		url : "findRefinementBand.spring",
		type : "post",
		dataType : "json",
		data:{
			sourceCountry:$("#SourceCountry").val(),
			targetCountry:$("#TargetCountry").val(),
			year:$("#Year").val(),
			weeks:$("#Week").selectpicker('val'),
			pls:$("#PL").selectpicker('val'),
			gls:$("#GL").selectpicker('val')
		},
		traditional:true,
		success : function(data) {
			var html="";
			var bands = new Array();
			$.each(data,function(n,v){
				html+='<option value="'+v.glanceViewBand+'">'+v.glanceViewBand+'</option>';
				bands.push(v.glanceViewBand);
			})
			$("#Band").html(html);
			$("#Band").selectpicker('val',bands);
			$('.selectpicker').selectpicker('refresh');
			getBrowseNodeCoverageData();
		}
	})
}

$(document).ready(function () {
	$("#SourceCountry").selectpicker({
		width : 80,
		noneSelectedText : 'None'
	})
	$("#TargetCountry").selectpicker({
		width : 80,
		noneSelectedText : 'None'
	})
	$("#Year").selectpicker({
		width : 80,
		noneSelectedText : 'None'
	})
	$("#Week").selectpicker({
		width : 180,
		noneSelectedText : 'None'
	})
	$("#PL").selectpicker({
		width : 180,
		noneSelectedText : 'None'
	})
	$("#GL").selectpicker({
		width : 180,
		noneSelectedText : 'None'
	})
	$("#Band").selectpicker({
		width : 180,
		noneSelectedText : 'None'
	})
	
	loadSourceCountry();
	//选择SourceCountry
	$("#SourceCountry").on('change',function(){
		loadTargetCountry();
	})
	//选择TargetCountry
	$("#TargetCountry").on('change',function(){
		loadYear();
	})
	//选择Year
	$("#TargetCountry").on('change',function(){
		loadWeek();
	})
	//选择Week
	$("#Week").on('change',function(){
		var array=$(this).selectpicker('val');
		if(array!=null){
			//加载PL数据
			loadPL();
		}else{
			$("#PL").html("");
			$("#GL").html("");
			$('.selectpicker').selectpicker('refresh');
			getBrowseNodeCoverageData();
		}
	})
	//选择PL
	$("#PL").on('change',function(){
		var array=$(this).selectpicker('val');
		if(array!=null){
			loadGL();
		}else{
			$("#GL").html("");
			$('.selectpicker').selectpicker('refresh');
			getBrowseNodeCoverageData();
		}
	})
	//选择GL
	$("#GL").on('change',function(){
		var array=$(this).selectpicker('val');
		if(array!=null){
			loadBand();
		}else{
			$("#Band").html("");
			$('.selectpicker').selectpicker('refresh');
			getBrowseNodeCoverageData();
		}
	})
	$("#Band").on('change',function(){
		getBrowseNodeCoverageData();
	})
	
	//下载Coverage
	$("#DownLoadCoverage").on('click',function(){
		var sourceCountry=$("#SourceCountry").val();
		var targetCountry=$("#TargetCountry").val();
		var weeks=$("#Week").selectpicker('val');
		var pls=$("#PL").selectpicker('val');
		var gls=$("#GL").selectpicker('val');
		var bands=$("#Band").selectpicker('val');
		var downLoadURL="./downLoadRefinementCoverage.spring?sourceCountry="
			  +sourceCountry+"&targetCountry="+targetCountry+"&year="+year+"&weeks="+weeks;
		if(pls!=null){
			downLoadURL+="&pls="+pls;
		}
		if(gls!=null){
			downLoadURL+="&gls="+gls;
		}
		if(bands!=null){
			downLoadURL+="&bands="+bands;
		}
		if(weeks!=null){
		  window.location.href=downLoadURL;
		}
		
	})
	
	
});