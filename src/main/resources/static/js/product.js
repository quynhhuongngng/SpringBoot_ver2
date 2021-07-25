
$('document').ready(function(){
	
	$('table #editProductButton').on('click',function(event){
		event.preventDefault();
			
		let href = $(this).attr('href');
		
		$.get(href, function(product, status){
			$('#idProduct').val(product.id);
			$('#discountProduct').val(product.discount);
			$('#booknameProduct').val(product.bookname);
			$('#priceProduct').val(product.price);
			$('#quantityProduct').val(product.quantity);
			$('#dateProduct').val(product.date);
			$('#authorProduct').val(product.author);
			$('#typeProduct').val(product.producttypeid);
			$('#descriptionProduct').val(product.description);
			$('#imageProduct').val(product.image);
			$('#availableProduct').val(product.available?'true':'false');
			$('#specialProduct').val(product.special?'true':'false');
		});					
		$('#editProductModal').modal();
	});
});