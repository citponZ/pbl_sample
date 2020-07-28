
$(document).on("click", ".add", function() {
    $(this).parent().clone(true).insertAfter($(this).parent());
    var textForm = document.getElementById("firsttext");
    textForm.value = '';
});
$(document).on("click", ".del", function() {
    var target = $(this).parent();
    if (target.parent().children().length > 1) {
        target.remove();
    }
});


$(function() {
    // 1. 「全選択」する
    $('#all').on('click', function() {
      $("input[name='deletedata[]']").prop('checked', this.checked);
    });
    // 2. 「全選択」以外のチェックボックスがクリックされたら、
    $("input[name='deletedata[]']").on('click', function() {
      if ($('#boxes :checked').length == $('#boxes :input').length) {
        // 全てのチェックボックスにチェックが入っていたら、「全選択」 = checked
        $('#all').prop('checked', true);
      } else {
        // 1つでもチェックが入っていたら、「全選択」 = checked
        $('#all').prop('checked', false);
      }
    });
  });