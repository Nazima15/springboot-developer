const modifyBtn = document.getElementById('modify-btn');
const titleInput = document.getElementById('title');
const contentInput = document.getElementById('content');

// newArticle.html(수정 화면)에서만 동작하도록 입력 요소가 있을 때만 바인딩
if (modifyBtn && titleInput && contentInput) {

    const params = new URLSearchParams(location.search);
    const id = params.get("id");

    modifyBtn.addEventListener('click', () => {

        fetch(`/api/articles/${id}`, {

            method: 'PUT',

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                title: titleInput.value,
                content: contentInput.value
            })

        })
            .then(() => {

                alert('수정이 완료되었습니다');

                location.replace(`/articles/${id}`);

            });

    });

}


// 등록 버튼
const createButton = document.getElementById('create-btn');

// newArticle.html(생성 화면)에서만 동작하도록 입력 요소가 있을 때만 바인딩
if (createButton && titleInput && contentInput) {

    createButton.addEventListener('click', () => {

        fetch('/api/articles', {

            method: 'POST',

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify({
                title: titleInput.value,
                content: contentInput.value
            })

        })
            .then(() => {

                alert('등록 완료');

                location.replace('/articles');

            });

    });

}

// article.html(상세 화면) 삭제 버튼
const deleteBtn = document.getElementById('delete-btn');

if (deleteBtn) {
    const id = deleteBtn.dataset.id;

    deleteBtn.addEventListener('click', () => {
        if (!id) return;
        if (!confirm('정말 삭제하시겠습니까?')) return;

        fetch(`/api/articles/${id}`, {
            method: 'DELETE'
        }).then(() => {
            location.replace('/articles');
        });
    });
}
