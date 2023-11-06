var slider=document.getElementById("slider");
var btn1=document.getElementById("slider-btn1");
var btn2=document.getElementById("slider-btn2");
var btn3=document.getElementById("slider-btn3");
var select=1;

function slider1(){
    
    select=1;
    imageUpdate();
}
function slider2(){
    
    select=2;
    imageUpdate();
}
function slider3(){
    select=3;
    imageUpdate();
}
function updateSelect(){
    if(select==1)
    {
        btn1.className="slider-select"
        btn2.className="slider-btn"
        btn3.className="slider-btn"
    }
    if(select==2)
    {
        btn1.className="slider-btn"
        btn2.className="slider-select"
        btn3.className="slider-btn"
    }
    if(select==3)
    {
        btn1.className="slider-btn"
        btn2.className="slider-btn"
        btn3.className="slider-select"
    }
}
function imageUpdate(){
    updateSelect();
    slider.style.animation="fade 1s linear infinite";
    setTimeout(() => {
        slider.style.animation="";
        }, 1000);
    setTimeout(() => {
        if(select==1)
        slider.style.backgroundImage="url(/img/slider1.png)";
    if(select==2)
    slider.style.backgroundImage="url(/img/product.png)";
    if(select==3)
    slider.style.backgroundImage="url(/img/cart.svg)";
    }, 500);
        
    
}
function del(){
    slider.style.animation="";
}
function switchImage(){
    if(select!=3)
        select++
    else
        select=1;
    imageUpdate();
}
setInterval(switchImage, 10000);