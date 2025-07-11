/**
 * ユーザー一覧画面
 */
$(function() {
    // テーブルの行をクリックしたときの処理
    $('#cardList tbody tr').on('click', function() {
        // すべての行の選択状態を解除
        $('#cardList tbody tr').removeClass('table-row-active');
        // クリックされた行に選択状態のクラスを追加
        $(this).addClass('table-row-active');
        // 編集ボタン、削除ボタンを活性化
        $('#editBtn').removeAttr('disabled');
        $('#deleteDummyBtn').removeAttr('disabled');
        
        // ログインID一時保管
        editSelectedCardId($(this));
        
        $('#deleteOkBtn').click(function() {
            $('#deleteBtn').trigger('click');
        });
    });
});

/**
 * テーブルで選択された行のログインIDを画面のhidden要素に保管します。
 * 
 * @param row 選択された行情報
 */
function editSelectedCardId(row) {
    row.find('td').each(function() {
        var columnId = $(this).attr('id');
        if (columnId.startsWith('cardId_')) {
            $('#selectedCardId').val($(this).text());
            return false;
        }
    });
}

//上下のスクロールバー連動
$(function () {
    //「対象要素.on( イベント名, 関数 )」
    //イベント名がscrollなので、画面がスクロールした時に発動
  $("#scrollbar, #scrollbox").on("scroll", function () {
    if ($(this).attr("id") === "scrollbar") {
        //scrollLeftでスクロール量を取得
      $("#scrollbox").scrollLeft($(this).scrollLeft());
    } else {
      $("#scrollbar").scrollLeft($(this).scrollLeft());
    }
  });
});

