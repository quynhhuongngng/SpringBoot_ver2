
$('document').ready(function(){
	
	$('table #editUserButton').on('click',function(event){
		event.preventDefault();
			
		let href = $(this).attr('href');
		
		$.get(href, function(user, status){
			$('#idEdit').val(user.id);
			$('#usernameEdit').val(user.username);
			$('#passwordEdit').val(user.password);
			$('#firstnameEdit').val(user.firstname);
			$('#lastnameEdit').val(user.lastname);
			$('#rolesEdit').val(user.roles);
			$('#enabledEdit').val(user.enabled?'true':'false');
			$('#imageEdit').val(user.photo);
		});					
		$('#editUserModal').modal();
	});

	$('table #deleteUserButton').on('click', function(event){
		event.preventDefault();
		
		let href= $(this).attr('href');
		
		$('#confirmDeleteButton').attr('href', href);
		
		$('#deleteUserModal').modal();
	});

	$('.table #photoButton').on('click',function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$('#photoModal #employeePhoto').attr('src', href);
		$('#photoModal').modal();		
	});
});