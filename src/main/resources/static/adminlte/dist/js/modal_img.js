// var options = {
// 	valueNames: [
// 		{name: 'grid-item-img', attr: 'src'}
// 	],
//     item: '<div class="grid-item" onclick="chooseImg(this)"><div class="img-container"><div class="img-div"><img class="grid-item-img" src=""></div></div></div>',
// 	pagination: true,
// 	page: 10
// };
//
// var values = [];
// var imgList = new List('list-user-img', options);
//
// function initListImg(arr) {
//     values = arr;
//     imgList.add(values);
// }

function closeChooseImgModal() {
    $('#choose-img-modal').modal('hide');
    $('#list-user-img .grid-item.choosen').removeClass('choosen');
    $('.btn-delete-img').prop('disabled', true);
    $('.btn-choose-img').prop('disabled', true);
}

$('.btn-delete-img').click(function() {
	let url = $('.grid-item.choosen .grid-item-img').attr('src');
	if (url == "" || url == null) {
		toastr.warning("Vui lòng chọn ảnh cần xóa");
		return;
	}
    let filename = url.replace('src/main/resources/static/uploads','');
	// Confirm
	let click = confirm("Bạn chắc chắn muốn xóa ảnh này?");
	if (click == true) {
		// Send api delete
		$.ajax({
        	url: '/api/delete-image/'+filename,
        	type: 'DELETE',
        	contentType: "application/json; charset=utf-8",
        	success: function(data) {
                // Remove from list
                var index = -1;
                var i = 0;
                for (i=0; i<values.length; i++) {
                    if (JSON.stringify(values[i]) === JSON.stringify({"grid-item-img": url}) ) {
                        index = i;
                        break;
                    }
                }
                if (index !== -1) {
                    values.splice(index, 1);
                }
                imgList.remove('grid-item-img', url);
        	},
        	error: function(data) {
        		toastr.warning(data.responseJSON.message);
        	}
        });
	}
})