$('#btn-upload-image').click(function () {
    $('#choose-img-modal').show();
    $('.btn-choose-img').on('click', function (event) {
        let url = $('#list-user-img .grid-item.choosen .grid-item-img').attr('src');
        if (url == "" || url == null) {
            toastr.warning("Bạn chưa chọn ảnh");
            return;
        }
        closeChooseImgModal();
        $('.thumbnail-container').show();
        $('#preview-img').attr('src', url);
    })
})

function removeProductImage(btn) {
    $(btn).parent().remove();
}

$("#upload-thumbnail").change(function () {
    var formUpload = $('#upload-images');
    var fd = new FormData(formUpload[0]);
    var file = $(this)[0].files[0];
    var fileName = file.name;
    var extension = fileName.substr((fileName.lastIndexOf('.') + 1));
    if (extension != "jpg" && extension != "png" && extension != "svg" && extension != "jpeg" && extension != "gif" && extension != "webp") {
        $(this).val('');
        toastr.warning("Chỉ hỗ trợ các định dạng ảnh: jpg, png, svg, jpeg, webp");
        return;
    }
    if (file.size > 2000000) {
        toastr.warning("Chỉ hỗ trợ file ảnh có kích thước lớn nhất 2MB");
        $(this).val('');
        return;
    }
    $.ajax({
        url: '/api/upload-file',
        type: 'POST',
        data: fd,
        contentType: false,
        processData: false,
        success: function (data) {
            $("#list_img_ul").append(`
                <div class="grid-item" onclick="chooseImg(this)">
                    <div class="img-container"><div class="img-div">
                        <img class="grid-item-img" src="${data}">
                    </div>
                </div>
                `)
        },
        error: function (data) {
            toastr.warning(data.responseJSON.message);
        }
    });
});

function chooseImg(element, shouldCloseModal) {
    // Kiểm tra xem ảnh có được chọn hay không
    var isChoosen = $(element).hasClass('choosen');

    // Nếu ảnh chưa được chọn, thêm class 'choosen' vào phần tử ảnh đang được chọn
    // và bật nút xóa ảnh và nút chọn ảnh
    if (!isChoosen) {
        $(element).addClass('choosen');
        $('.btn-delete-img, .btn-choose-img').prop('disabled', false);
    }
        // Nếu ảnh đã được chọn, loại bỏ class 'choosen' khỏi phần tử ảnh đang được chọn
        // và vô hiệu hóa nút xóa ảnh và nút chọn ảnh
    else {
        $(element).removeClass('choosen');
        $('.btn-delete-img, .btn-choose-img').prop('disabled', true);
    }

    // Đặt đường dẫn của hình ảnh đã chọn vào input ẩn 'image'
    var selectedImgSrc = $(element).find('.grid-item-img').attr('src');
    $('#image').val(selectedImgSrc);

    // Cập nhật ảnh xem trước
    $('#preview-img').attr('src', selectedImgSrc);

    // Nếu cần đóng modal, đóng modal
    if (shouldCloseModal) {
        $('#choose-img-modal').modal('hide');
    }
}
$('.btn-choose-img').click(function() {
    // Chọn ảnh và đóng modal
    var selectedImg = $('.grid-item.choosen');
    if (selectedImg.length > 0) {
        chooseImg(selectedImg, true);
    }
});

renderImages()

function renderImages() {
    $.ajax({
        url: '/api/files',
        type: 'GET',
        contentType: false,
        processData: false,
        success: function (dataRespose, data) {
            let urlList = dataRespose.content;
            for (let i = 0; i < urlList.length; i++) {
                $("#list_img_ul").append(`
                    <div class="grid-item" onclick="chooseImg(this)">
                        <div class="img-container"><div class="img-div">
                            <img class="grid-item-img" src="${urlList[i]}">
                        </div>
                    </div>
                    `)
            }
        },
        error: function (data) {
            toastr.warning(data.responseJSON.message);
        }
    });
}