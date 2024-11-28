document.addEventListener('DOMContentLoaded', function() {
    // 回答セクションを初期状態で非表示に設定
    const answerSection = document.getElementById('answerSection');
    const showAnswerButton = document.getElementById('showAnswerButton');

    if (showAnswerButton) {
        showAnswerButton.addEventListener('click', function() {
            if (answerSection) {
                answerSection.style.display = 'block';
                showAnswerButton.style.display = 'none'; // ボタンを非表示にする
            }
        });
    }
});