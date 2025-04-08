/**
 * 
 */
const quality = 0.6;
const maxWidth = 800;
const maxSizeMB = 5;

function compressAndReplace(input) {
  const file = input.files[0];
  if (!file) return;

  new Compressor(file, {
    quality: quality,
    maxWidth: maxWidth,
    success(result) {
      const compressedSizeMB = result.size / (1024 * 1024);
      if (compressedSizeMB > maxSizeMB) {
        alert('圧縮後も5MBを超えているためアップロードできません。');
        input.value = '';
        return;
      }

      // 圧縮されたファイルで置き換える
      const dataTransfer = new DataTransfer();
      const compressedFile = new File([result], file.name, { type: result.type });
      dataTransfer.items.add(compressedFile);
      input.files = dataTransfer.files;
    },
    error(err) {
      console.error('圧縮エラー:', err.message);
    }
  });
}

// すべての画像inputに適用
document.querySelectorAll('.image-input').forEach(input => {
  input.addEventListener('change', () => compressAndReplace(input));
});
