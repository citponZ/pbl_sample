const defaultDispCnt = 2; // 初期表示件数
const addDispCnt = 3;     // 追加表示件数

(function ($) {
  $(function () {
    let maxDispCnt = 0;     // 最大表示件数
    let currentDispCnt = 0; // 現在の表示件数
    let tileList = $('#tileList').children('tr th:each="words : ${wordsList}" th:object="${words}"'); // 一覧のli子要素をすべて取得

    // 一覧の初期表示
    $(tileList).each(function (i, elem) {
      // 初期表示件数のみ表示
      if (i < defaultDispCnt) {
        $(this).show();
        currentDispCnt++;
      }
      maxDispCnt++;

      // もっと見るボタンを表示
      let displayed = 0;
      if (maxDispCnt > currentDispCnt && !displayed) {
        $('.readMoreBtn').show();
        displayed = 1;
      }
    });

    // もっと見るボタンクリックイベント
    $('.readMoreBtn').click(function () {
      let newCount = currentDispCnt + addDispCnt; // 新しく表示する件数

      // 新しく表示する件数のみ表示
      $(tileList).each(function (i, elem) {
        if (currentDispCnt <= i && i < newCount) {
          $(this).show();
          currentDispCnt++;
        }
      });
