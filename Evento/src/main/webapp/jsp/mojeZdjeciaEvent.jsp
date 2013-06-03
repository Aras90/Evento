<%@taglib uri="/struts-tags" prefix="s"%>
<style>
#main {
	padding-bottom: 0
}
</style>
<script>
	(function(d, s, id) {
		var js, fjs = d.getElementsByTagName(s)[0];
		if (d.getElementById(id))
			return;
		js = d.createElement(s);
		js.id = id;
		js.src = "//connect.facebook.net/pl_PL/all.js#xfbml=1&appId=487331951334796";
		fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));
	$(function() {
		$('.fancybox').fancybox();
	})
	
	function usun(id){
		var r = confirm("Czy jesteś pewien, że chcesz usunąć to zdjęcie?");
		if (r == true) {
		$('#img' + id).remove();
		
		$.post(
				'deletePhoto.action',
				{
					idPicture : id,
				},
				function(data) {				
				},
				'json'
			);
		}
	}
</script>
<div class="title">
	<s:property value="Name" />
</div>
<div class="zdjecia">
	<s:iterator value="picturesList" status="stat">
	
		<div class="zdjecie" id = "img<s:property value="picturesList [# stat.index][0].Id_Picture" />">
			<a class="fancybox" 
				title="<s:property value="picturesList [# stat.index][0].Name" />"
				data-fancybox-group="gallery"
				data-id="<s:property value="picturesList [# stat.index][0].Id_Picture" />"
				data-glos="<s:property value="picturesList [# stat.index][1].Value" />"
				href="<s:property value="picturesList [# stat.index][0].TymczasowyBezposredniLink" />">
				<img rel="foto" id="<s:property value="#stat.index" />"
				src="<s:property value="picturesList [# stat.index][0].TymczasowyBezposredniLink" />" />
				<br>
			</a>
			
			
			
			<s:property value="picturesList [# stat.index][0].Name" />
			
			
		<!-- 	<s:if test="check()" >
			
				<input type="button" value="edytuj"
				onclick="edytuj('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" />
			
				<input id="usun" type="button" value="usun"
				onclick="usun('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" />
			
			</s:if>
			
			<s:else>
			
					asdasdasasd
			</s:else>
		-->	
		
		
				<input type="button" value="edytuj"
				onclick="edytuj('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" />
			
				<input id="usun" type="button" value="usun"
				onclick="usun('<s:property value="picturesList [# stat.index][0].Id_Picture" />')" />
				
				
			<s:set name="Id_Picture" value="picturesList [# stat.index][0].Id_Picture" /> 
			
			<!-- <s:form action="delete">
                    <s:submit type="button" value="Usun"/>
			</s:form>
			-->
			
			<br> Utworzono:
			<s:property value="picturesList [# stat.index][0].CreatedAt" />
			
		</div>

	</s:iterator>
	

	<script>
		
		/*EDYCJA ZDJEC*/
		Filters={};
Filters.canvasArray=new Array();
Filters.lastFilter=new function(){};
Filters.currentStep=0;
Filters.stepsForward=0;
Filters.stepsBack=0;
Filters.flag=false;
Filters.image=new Image;
Filters.cropping = true;
Filters.cropData = null;
Filters.lastFilteredStep = 0;
Filters.lastVal = new Array();
Filters.imageid='';
Filters.build = function(imageid){
	Filters.imageid=imageid;
	var image=document.getElementById(imageid);
	var container=document.getElementById('edytor-container');
	if(!container){container=document.createElement("div");
	container.id="edytor-container";}
	container.innerHTML='<div id="edytor-blackout"></div><div id="edytor-wrapper"><div id="edytor-leftnav">'+
			'<button class="edytor-button" onclick=\'Filters.startCrop()\'>Crop</button>'+
			'<button class="edytor-button" onclick=\'Filters.filterImage(Filters.grayscale)\'>Grayscale</button>'+
			'<button class="edytor-button" onclick=\'Filters.filterImage(Filters.sepia)\'>Sepia</button>'+
            '<button class="edytor-button" onclick=\'Filters.filterImage(Filters.sharpen)\'>Sharpen</button>'+
            '<button class="edytor-button" onclick=\'Filters.filterImage(Filters.blur)\'>Blur</button>'+
            '<button class="edytor-button" onclick=\'Filters.toasterx()\'>Toaster</button>'+
			'<button class="edytor-button" onclick=\'Filters.save()\'>Save</button>'+
			'<div><label for="rotateinput">rotate:</label> <input id="rotateinput" type="range" min="0" max="360" step="10" value="0" onchange=\'rotatevalue.value=value;Filters.rotate(value)\'></input> <output id="rotatevalue">0</output></div>'+
			'<div><label for="scaleinput">scale:</label> <input id="scaleinput" type="range" min="10" max="1000" step="10" value="100" onchange=\'scalevalue.value=value;Filters.scale(value)\'></input> <output id="scalevalue">100</output></div>'+
			'<div><label for="lightinput">Light:</label> <input id="lightinput" type="range" min="-255" max="255" step="5" value="0" onchange=\'lightvalue.value=value;Filters.filterImage(Filters.hls,value,"l")\'></input> <output id="lightvalue">0</output></div>'+
			'<div><label for="contrastinput">contrast:</label> <input id="contrastinput" type="range" min="0.1" max="10" step="0.1" value="1" onchange=\'contrastvalue.value=value;Filters.filterImage(Filters.contrast,value)\'></input> <output id="contrastvalue">1</output></div>'+
			'<button class="edytor-button" id="back" onclick=\'Filters.stepBack()\' disabled><</button>'+
			'<button class="edytor-button" id="forward" onclick=\'Filters.stepForward()\' disabled>></button>'+
		'</div><div id="edytor-content"></div><div>';
		document.body.appendChild(container);
		Filters.start();
}

Filters.start = function(imageid){
	Filters.canvasArray=new Array();
	Filters.lastFilters=new Array();
	Filters.currentStep=0;
	Filters.stepsForward=0;
	Filters.stepsBack=0;
	Filters.flag=false;
	Filters.image=new Image;
	Filters.cropping = true;
	Filters.cropData = null;
	Filters.lastFilteredStep = 0;
	for(var i = 0; i<=8;i++){
		Filters.canvasArray[i] = document.createElement('canvas');
		Filters.canvasArray[i].classList.add('edytor-hidden');
		document.getElementById("edytor-content").appendChild(Filters.canvasArray[i]);
		tool = new tools['rect']();
	}
	
	for(var i = 0; i<=7;i++){
		Filters.lastFilters[i]=new function(){};
	}
	for(var i = 0; i<=7;i++){
		Filters.lastVal[i]=0;
	}
	Filters.init(document.getElementById(Filters.imageid));
}


Filters.init = function(img) {
	var c = this.canvasArray[0];
	  i=8;

	  while(img.src[i]!='/')i++;
	  var iiimg = new Image();
	  iiimg.onload = function() {
		  c.width = img.width;
		  c.height = img.height;
	 
	 var ctx = c.getContext('2d');
	 ctx.drawImage(iiimg,0,0,iiimg.naturalWidth,iiimg.naturalHeight,0,0,c.width,c.height);
	 c.classList.remove('edytor-hidden');
	 css('#edytor-wrapper', 'margin-left', (-c.width/2-145)+'px');
	 css('#edytor-wrapper', 'margin-top', -c.height/2+'px');
	}

	  iiimg.crossOrigin=img.src.substring(0,i+2)+"crossdomain.xml"
	  iiimg.src=img.src;
	    
	 };

Filters.getPixels = function(c) {
  var ctx = c.getContext('2d');
  var iData=ctx.getImageData(0,0,c.width,c.height);
  return iData;
};


Filters.setStepsBack=function(value){
	this.stepsBack = value;
	if(this.stepsBack==0)document.getElementById("forward").disabled = true;
	else document.getElementById("forward").disabled = false;
}
Filters.setStepsForward=function(value){
	this.stepsForward = value;
	if(this.stepsForward==0)document.getElementById("back").disabled = true;
	else document.getElementById("back").disabled = false;
}


Filters.stepBack = function(){
	if(this.currentStep!=0){
			if(this.stepsForward>0){
			    switch(this.lastFilters[this.currentStep]){
					case Filters.scale:
						Filters.lastVal[this.stepsBack] = document.getElementById("scaleinput").value;
						document.getElementById("scaleinput").value=100;
						document.getElementById("scalevalue").innerHTML=100;
					break;
					case Filters.rotate:
						Filters.lastVal[this.stepsBack] = document.getElementById("rotateinput").value;
						document.getElementById("rotateinput").value=0;
						document.getElementById("rotatevalue").innerHTML=0;
					break;
					case Filters.hls:
						Filters.lastVal[this.stepsBack] = document.getElementById("lightinput").value;
						document.getElementById("lightinput").value=0;
						document.getElementById("lightvalue").innerHTML=0;
					break;
					case Filters.contrast:
						Filters.lastVal[this.stepsBack] = document.getElementById("contrastinput").value;
						document.getElementById("contrastinput").value=1;
						document.getElementById("contrastvalue").innerHTML=1;
					break;
				}
				var lastCanvas = this.canvasArray[this.currentStep];
				this.setStepsForward(this.stepsForward-1);
				this.currentStep =  (this.currentStep == 1) ? ((this.flag==false)? 0 : 7) : --this.currentStep;
				var c = this.canvasArray[this.currentStep];
				lastCanvas.classList.add('edytor-hidden');
				c.classList.remove('edytor-hidden');
				this.setStepsBack(this.stepsBack+1);

			}
	}
}
Filters.stepForward = function(){
			if(this.stepsBack>0){
				var lastCanvas = this.canvasArray[this.currentStep];
				
				this.currentStep%=7;
				this.currentStep++;
				var c = this.canvasArray[this.currentStep];
				lastCanvas.classList.add('edytor-hidden');
				c.classList.remove('edytor-hidden');
				if(this.stepsForward<5)this.setStepsForward(this.stepsForward+1);
				this.setStepsBack(this.stepsBack-1);
				switch(this.lastFilters[this.currentStep]){
					case Filters.scale:
						document.getElementById("scaleinput").value=this.lastVal[this.stepsBack];
						document.getElementById("scalevalue").innerHTML=this.lastVal[this.stepsBack];
					break;
					case Filters.rotate:
						document.getElementById("rotateinput").value=this.lastVal[this.stepsBack];
						document.getElementById("rotatevalue").innerHTML=this.lastVal[this.stepsBack];
					break;
					case Filters.hls:
						document.getElementById("lightinput").value=this.lastVal[this.stepsBack];
						document.getElementById("lightvalue").innerHTML=this.lastVal[this.stepsBack];
					break;
					case Filters.contrast:
						document.getElementById("contrastinput").value=this.lastVal[this.stepsBack];
						document.getElementById("contrastvalue").innerHTML=this.lastVal[this.stepsBack];
					break;
				}

			}
}

Filters.rotate=function(angle){
	var step = this.currentStep;
	if(this.lastFilters[this.currentStep]==Filters.rotate){
			if(this.currentStep!=0){
				this.setStepsForward(this.stepsForward-1);
				this.currentStep =  (this.currentStep == 1) ? ((this.flag==false)? 0 : 7) : --this.currentStep;
				step=this.lastFilteredStep;
			}
	}
	else this.lastFilteredStep=step;
	var lastCanvas = this.canvasArray[step];
	if(this.stepsForward<5)this.setStepsForward(this.stepsForward+1);
	this.setStepsBack(0);
	if(this.currentStep==7)this.flag=true;
	this.currentStep%=7;
	this.currentStep++;
	
	var c = this.canvasArray[8];

	if(Filters.lastFilter!=Filters.rotate){
		c.width=lastCanvas.width;
		c.height=lastCanvas.height;
		var d = Math.sqrt(Math.pow(c.width,2)+Math.pow(c.height,2));
		c.width = d;
		c.height = d;
	}
	var ctx = c.getContext('2d');
	ctx.save(); 
	ctx.clearRect(0,0,c.width,c.height);
	ctx.translate(c.width/2,c.width/2);
	ctx.rotate(angle *  Math.PI/180);
	ctx.drawImage(lastCanvas, -(lastCanvas.width/2), -(lastCanvas.height/2));
	ctx.restore(); 
	//przeliczyc liczbe wolnych kolumn i wierszy
	var i = 3;
	var rows = 0;
	var columns = 0;
	var column = 0;
	var pData = [this.getPixels(c)];
	while(pData[0].data[i]==0){
		i+=4;
		rows++;
	}
	i=3;
	while(pData[0].data[i]==0){
		i+=c.width*4;
		columns++;
		if(columns/c.height==1){
			column++;
			columns=0;
			i=3+4*column;
		}
	}
	rows = Math.floor(rows / c.width);
	var c2 = this.canvasArray[this.currentStep];
	c2.width=c.width-2*column;
	c2.height=c.height-2*rows;
	ctx = c2.getContext('2d');
	ctx.drawImage(c, column, rows, c2.width , c2.height, 0,0, c2.width , c2.height);
	lastCanvas.classList.add('edytor-hidden');
	c2.classList.remove('edytor-hidden');
	this.lastFilters[this.currentStep]=Filters.rotate;
}

Filters.scale=function(scale){
	var step = this.currentStep;
	if(this.lastFilters[this.currentStep]==Filters.scale){
			if(this.currentStep!=0){
				var lastCanvas = this.canvasArray[this.currentStep];
				this.setStepsForward(this.stepsForward-1);
				this.currentStep =  (this.currentStep == 1) ? ((this.flag==false)? 0 : 7) : --this.currentStep;
				step=this.lastFilteredStep;
			}
	}
	else this.lastFilteredStep=step;
	var lastCanvas = this.canvasArray[step];
	if(this.stepsForward<5)this.setStepsForward(this.stepsForward+1);
	this.setStepsBack(0);
	if(this.currentStep==7)this.flag=true;
	this.currentStep%=7;
	this.currentStep++;
	var c = this.canvasArray[this.currentStep];
		c.width = lastCanvas.width*scale/100;
		c.height = lastCanvas.height*scale/100;
	if(c.width > 3872 || c.height> 2592 ){ 
		var scale1,scale2=0;
		scale1=3872/lastCanvas.width*100;
		scale2=2592/lastCanvas.height*100;
		scale = (scale1<scale2)?scale1:scale2;
		c.width = lastCanvas.width*scale/100;
		c.height = lastCanvas.height*scale/100;
	}
	else if(c.width < 16 || c.height < 16){
		var scale1,scale2=0;
		scale1=16/lastCanvas.width*100;
		scale2=16/lastCanvas.height*100;
		scale = (scale1>scale2)?scale1:scale2;
		c.width = lastCanvas.width*scale/100;
		c.height = lastCanvas.height*scale/100;
	}
	
	var ctx = c.getContext('2d');
	ctx.save(); 
	ctx.scale(scale/100,scale/100);
		ctx.drawImage(lastCanvas, 0, 0);
		ctx.restore(); 
	// and restore the co-ords to how they were when we began

	lastCanvas.classList.add('edytor-hidden');
	c.classList.remove('edytor-hidden');
	this.lastFilters[this.currentStep]=Filters.scale;
}

Filters.startCrop=function(){
	if(this.lastFilters[this.currentStep]!=Filters.startCrop){
	this.lastFilteredStep = this.currentStep;
	var lastCanvas = this.canvasArray[this.currentStep];
	if(this.stepsForward<5)this.setStepsForward(this.stepsForward+1);
	this.setStepsBack(0);
	if(this.currentStep==7)this.flag=true;
	this.currentStep%=7;
	this.currentStep++;
	var c = this.canvasArray[this.currentStep];
	c.width = lastCanvas.width;
	c.height = lastCanvas.height;
	var ctx = c.getContext('2d');
	lastCanvas.classList.add('edytor-hidden');
	c.classList.remove('edytor-hidden');
	ctx.drawImage(lastCanvas,0,0);
	//this.cropData=[this.getPixels(lastCanvas)].data;
	c.addEventListener('mousedown', Filters.ev_canvas, false);
	c.addEventListener('mousemove', Filters.ev_canvas, false);
    c.addEventListener('mouseup',   Filters.ev_canvas, false);
	this.lastFilters[this.currentStep]=Filters.startCrop;
	}
}

Filters.stopCrop=function(x,y,w,h){
var retVal = confirm("Do you want to crop ?");
    var c = this.canvasArray[this.currentStep];
	var ctx = c.getContext('2d');
   if( retVal == true ){

	c.width = w;
	c.height = h;

	ctx.drawImage(this.canvasArray[this.lastFilteredStep], x, y, w, h, 0,0, w, h);
}
   else{
		ctx.clearRect(0,0,c.width,c.height);
		ctx.drawImage(Filters.canvasArray[Filters.lastFilteredStep],0,0);

}
	c.removeEventListener('mousedown', Filters.ev_canvas, false);
	c.removeEventListener('mousemove', Filters.ev_canvas, false);
        c.removeEventListener('mouseup',   Filters.ev_canvas, false);
	this.lastFilters[this.currentStep]=Filters.stopCrop;

}

Filters.filterImage = function(filter,var_args) {

  if(this.lastFilters[this.currentStep]==filter){
		if(this.currentStep != 0){
		this.currentStep =  (this.currentStep == 1) ? ((this.flag==false)? 0 : 7) : --this.currentStep;
		if(this.stepsForward>0)this.setStepsForward(this.stepsForward-1);}
  }
  var lastCanvas = this.canvasArray[this.currentStep];
  var args = [this.getPixels(lastCanvas)];
  for (var i=1; i<arguments.length; i++) {
    args.push(arguments[i]);
  }
  
  if(this.stepsForward<5)this.setStepsForward(this.stepsForward+1);
  this.setStepsBack(0);
  if(this.currentStep==7)this.flag=true;
  this.currentStep%=7;
  this.currentStep++;
  
  var c = this.canvasArray[this.currentStep];
  c.width = lastCanvas.width;
  c.height = lastCanvas.height;
  this.iData=filter.apply(null,args);
  var ctx = c.getContext('2d');
  ctx.putImageData(this.iData,0,0);
  lastCanvas.classList.add('edytor-hidden');
  c.classList.remove('edytor-hidden');
  this.lastFilters[this.currentStep]=filter;
  
};

Filters.grayscale = function(pixels, args) {
  var pixArray = pixels.data;
  for (var i=0; i<pixArray.length; i+=4) {
    var r = pixArray[i];
    var g = pixArray[i+1];
    var b = pixArray[i+2];
    var v = 0.2126*r + 0.7152*g + 0.0722*b;
    pixArray[i] = pixArray[i+1] = pixArray[i+2] = v
  }
  return pixels;
};

Filters.sepia = function(pixels, args) {
  var sepiaIntensity = 20;
  var sepiaDepth = 30;
  var pixArray = pixels.data;
  for (var i=0; i<pixArray.length; i+=4) {

    var r = pixArray[i];
    var g = pixArray[i+1];
    var b = pixArray[i+2];
    var v = 0.2126*r + 0.7152*g + 0.0722*b;
        r = g = b = v;
        r = r + (sepiaDepth * 2);
        g = g + sepiaDepth;

        if (r>255) r=255;
        if (g>255) g=255;
        if (b>255) b=255;

        // Darken blue color to increase sepia effect
        b-= sepiaIntensity;

        // normalize if out of bounds
        if (b<0) b=0;
        if (b>255) b=255;

        pixArray[i] = r;
        pixArray[i+1]= g;
        pixArray[i+2] = b;
  }
  return pixels;
  };
Filters.hls = function(pixels,value,modifier) {
  var pixArray = pixels.data;
  for (var i=0; i<pixArray.length; i+=4) {
    var r = pixArray[i];
    var g = pixArray[i+1];
    var b = pixArray[i+2];
    var pixel=rgbc(r,g,b,value,modifier);
    pixArray[i] = pixel.r;
	pixArray[i+1] = pixel.g;
	pixArray[i+2] = pixel.b;
  }
  return pixels;
};

Filters.contrast = function(pixels, factor){
  var data = pixels.data;
  

   var tmp = new Array();

   for (var i = 0; i < data.length; i += 4) {
			tmp[0]= factor * (data[i] - 128) + 128;
			tmp[1]= factor * (data[i+1] - 128) + 128;
			tmp[2]= factor * (data[i+2] - 128) + 128;
			data[i] = tmp[0];
			data[i+1] = tmp[1];
			data[i+2] = tmp[2];
   }
	return pixels;

}
Filters.blur = function(pixels, weights, opaque) {
  var opaque = 0;
  var weights = [  1/9, 1/9, 1/9,
				  1/9,  1/9, 1/9,
				   1/9, 1/9,  1/9 ];
  var side = Math.round(Math.sqrt(weights.length));
  var halfSide = Math.floor(side/2);
  var src = pixels.data;
  var sw = pixels.width;
  var sh = pixels.height;
  // pad output by the convolution matrix
  var w = sw;
  var h = sh;
  var output = Filters.getPixels(Filters.canvasArray[Filters.currentStep]);
  var dst = output.data;
  // go through the destination image pixels
  var alphaFac = opaque ? 1 : 0;
  for (var y=0; y<h; y++) {
    for (var x=0; x<w; x++) {
      var sy = y;
      var sx = x;
      var dstOff = (y*w+x)*4;
      // calculate the weighed sum of the source image pixels that
      // fall under the convolution matrix
      var r=0, g=0, b=0, a=0;
      for (var cy=0; cy<side; cy++) {
        for (var cx=0; cx<side; cx++) {
          var scy = sy + cy - halfSide;
          var scx = sx + cx - halfSide;
          if (scy >= 0 && scy < sh && scx >= 0 && scx < sw) {
            var srcOff = (scy*sw+scx)*4;
            var wt = weights[cy*side+cx];
            r += src[srcOff] * wt;
            g += src[srcOff+1] * wt;
            b += src[srcOff+2] * wt;
            a += src[srcOff+3] * wt;
          }
        }
      }
      dst[dstOff] = r;
      dst[dstOff+1] = g;
      dst[dstOff+2] = b;
      dst[dstOff+3] = a + alphaFac*(255-a);
    }
  }
  return output;
};
Filters.sharpen = function(pixels, weights, opaque) {
  var opaque = 0;
  var weights = [  0, -1,  0,
				  -1,  5, -1,
				   0, -1,  0 ];
  var side = Math.round(Math.sqrt(weights.length));
  var halfSide = Math.floor(side/2);
  var src = pixels.data;
  var sw = pixels.width;
  var sh = pixels.height;
  // pad output by the convolution matrix
  var w = sw;
  var h = sh;
  var output = Filters.getPixels(Filters.canvasArray[Filters.currentStep]);
  var dst = output.data;
  // go through the destination image pixels
  var alphaFac = opaque ? 1 : 0;
  for (var y=0; y<h; y++) {
    for (var x=0; x<w; x++) {
      var sy = y;
      var sx = x;
      var dstOff = (y*w+x)*4;
      // calculate the weighed sum of the source image pixels that
      // fall under the convolution matrix
      var r=0, g=0, b=0, a=0;
      for (var cy=0; cy<side; cy++) {
        for (var cx=0; cx<side; cx++) {
          var scy = sy + cy - halfSide;
          var scx = sx + cx - halfSide;
          if (scy >= 0 && scy < sh && scx >= 0 && scx < sw) {
            var srcOff = (scy*sw+scx)*4;
            var wt = weights[cy*side+cx];
            r += src[srcOff] * wt;
            g += src[srcOff+1] * wt;
            b += src[srcOff+2] * wt;
            a += src[srcOff+3] * wt;
          }
        }
      }
      dst[dstOff] = r;
      dst[dstOff+1] = g;
      dst[dstOff+2] = b;
      dst[dstOff+3] = a + alphaFac*(255-a);
    }
  }
  return output;
};

Filters.toaster = function(pixels, args) {
  var toasterIntensity = 0.2;
  
  
  var pixArray = pixels.data;
  for (var i=0; i<pixArray.length; i+=4) {
    var r = pixArray[i];
    var g = pixArray[i+1];
    var b = pixArray[i+2];
		
		r = toasterIntensity * 255 + (1 - toasterIntensity) * r
        g = (1 - toasterIntensity) * g
		b = (1 - toasterIntensity) * b

        if (r>255) r=255;
        if (g>255) g=255;
        if (b>255) b=255;

                

        pixArray[i] = r;
        pixArray[i+1]= g;
        pixArray[i+2] = b;
  }
  
  
  
  return pixels;
  };

Filters.toasterx = function(){

Filters.filterImage(Filters.toaster);
var c = Filters.canvasArray[Filters.currentStep];
var ctx=c.getContext("2d");
var grd=ctx.createRadialGradient(c.width/2,c.height/2,c.width/10,c.width/2,c.height/2,c.width/2);
grd.addColorStop(0,"transparent");
grd.addColorStop(1,"black");
ctx.fillStyle=grd;
ctx.fillRect(0,0,c.width,c.height);


}
Filters.ev_canvas = function(ev) {
    var pos = findPos(document.querySelectorAll("canvas")[Filters.currentStep]);
    ev._x = ev.pageX-pos.x;
    ev._y = ev.pageY-pos.y;


    // Call the event handler of the tool.
	var func = tool[ev.type];
    if (func) {
      func(ev);
    }
}
tools = {};
tools.rect = function () {
    var tool = this;
    this.started = false;
    var x,y,w,h;
    this.mousedown = function (ev) {
      tool.started = true;
      tool.x0 = ev._x;
      tool.y0 = ev._y;
    };

    this.mousemove = function (ev) {
      if (!tool.started) {
        return;
      }

       x = Math.min(ev._x,  tool.x0);
          y = Math.min(ev._y,  tool.y0);
          w = Math.abs(ev._x - tool.x0);
          h = Math.abs(ev._y - tool.y0);
		

      if (!w || !h) {
        return;
      }
		var c = Filters.canvasArray[Filters.currentStep];
		var context = c.getContext('2d');
		context.clearRect(0,0,c.width,c.height);
		context.drawImage(Filters.canvasArray[Filters.lastFilteredStep],0,0);
		//context.putImageData(Filters.cropData,0,0);
		context.fillStyle= "#000000";
		context.globalAlpha=0.8;
		context.fillRect(0, 0, c.width, y);
		context.fillRect(0, y+h , c.width, c.height-(y+h));
		context.fillRect(0, y, x,  h);
		context.fillRect(x+w, y, c.width-(x+w),  h);
                context.globalAlpha=1;
    };

    this.mouseup = function (ev) {
      if (tool.started) {
        tool.mousemove(ev);
        tool.started = false;
		Filters.stopCrop(x,y,w,h);
      }
    };
  };


function rgbc(r,g,b,value,modified) {
    r /= 255;
    g /= 255;
    b /= 255;
    //RGBtoHSL
    var rgbmax = Math.max(r, g, b), rgbmin = Math.min(r, g, b);
    var h, s, l = (rgbmax + rgbmin) / 2;
    if (rgbmax === rgbmin) {
        h = s = 0;
    } else {
        var d = rgbmax - rgbmin;
        s = l > 0.5 ? d / (2 - rgbmax - rgbmin) : d / (rgbmax + rgbmin);
        switch (rgbmax) {
            case r: h = (g - b) / d + (g < b ? 6 : 0); break;
            case g: h = (b - r) / d + 2; break;
            case b: h = (r - g) / d + 4; break;
        }
        h /= 6;
    }
	
	switch (modified) {
            case 'l':     
				l += value/255;
				if (l < 0) {l = 0;}
				if (l > 1) {l = 1;}; 
				break;
            case 'h':
				h += h * (value / 100);
				if (h < 0) {h = 0;}
				if (h > 1) {h = 1;}; 
				break;
            case 's': 
				s += s * (value / 100);
				if (s < 0) {s = 0;}
				if (s > 1) {s = 1;}; 
				break;
        }

    //HSLtoRGB
    var r, g, b;
    if (s === 0) {
        r = g = b = l;
    } else {
        function hc(p, q, t) {
            if (t < 0) t += 1;
            if (t > 1) t -= 1;
            if (t < 1 / 6) return p + (q - p) * 6 * t;
            if (t < 1 / 2) return q;
            if (t < 2 / 3) return p + (q - p) * (2 / 3 - t) * 6;
            return p;
        }
        var q = l < 0.5 ? l * (1 + s) : l + s - l * s;
        var p = 2 * l - q;
        r = hc(p, q, h + 1 / 3);
        g = hc(p, q, h);
        b = hc(p, q, h - 1 / 3);
    }
    r = (Math.round(r * 255));
    g = (Math.round(g * 255));
    b = (Math.round(b * 255));
    var pixel={};
	pixel.r=r;
	pixel.b=b;
	pixel.g=g;
	return pixel;
}

function css(selector, property, value) {
    for (var i=0; i<document.styleSheets.length;i++) {//Loop through all styles
        //Try add rule
        try { document.styleSheets[i].insertRule(selector+ ' {'+property+':'+value+'}', document.styleSheets[i].cssRules.length);
        } catch(err) {try { document.styleSheets[i].addRule(selector, property+':'+value);} catch(err) {}}//IE
    }
}

Filters.save = function(){
	document.getElementById(Filters.imageid).width = this.canvasArray[this.currentStep].width;
	document.getElementById(Filters.imageid).height = this.canvasArray[this.currentStep].height;
	document.getElementById(Filters.imageid).src=this.canvasArray[this.currentStep].toDataURL("image");
	var a = document.getElementById(Filters.imageid);
	var node = document.getElementById('edytor-content');
	while (node.hasChildNodes()) {
    node.removeChild(node.lastChild);
	}
	var image=document.getElementById(Filters.imageid);
	document.getElementById('edytor-container').innerHTML='';
	
	
	/*$.ajaxSettings.traditional = true;
	var request1 = $.ajax({
		type : 'POST',
		url : 'testZip.action',
		data : {
			invoke : invoke,
			namePhoto : namePhoto
		}
	});*/
	
	
	
}
function findPos(obj) {
    var curleft = 0, curtop = 0;
    if (obj.offsetParent) {
        do {
            curleft += obj.offsetLeft;
            curtop += obj.offsetTop;
        } while (obj = obj.offsetParent);
        return { x: curleft, y: curtop };
    }
    return undefined;
}
/*window.onload = function () {

	//dodawanieListenerow;
	var images=document.querySelectorAll(".fancybox img");
	for(var i=0;i<images.length;i++)images[i].addEventListener('click',
	(function() {
        var string = images[i].id;
        return function() {
            Filters.build(string);
        }
    })());

}*/
function edytuj(s){
	
	Filters.build(s);
	
};
	</script>
</div>
<br style="clear: both">
