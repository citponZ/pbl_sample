$(function(){
    $('.rankTable').hide();
    $('.wordTable').hide();


    $('#rankingButton').click(function(){
        $('.wordTable').hide();
        $('.rankTable').fadeIn();
    });
    /*$('.wordTable').fadeOut();*/
    $('#wordButton').click(function(){
        $('.rankTable').hide();
        $('.wordTable').fadeIn();
    });
});