
$(function(){
     GOOD 
    $(document).on('click', '.clickme_btn', function(){
      alert('Button is clicked');
    });
    
    $('#add_clickme_btn_btn').on('click', function(){
      $('#btns_box').append(
        '<button class="clickme_btn">Click Me</button>'
      );
    });
  });
  /*
 window.onload = function() {
  setInterval(function() {
    var dd = new Date();
    document.getElementById("T1").innerHTML = dd.toLocaleString();
  }, 1000);
}
  */
