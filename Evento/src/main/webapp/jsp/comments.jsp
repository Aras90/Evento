<%@taglib uri="/struts-tags" prefix="s" %>
<script>
	function dodaj(){
		$.post(
			'addComments.action',
			{
				Id_Picture : $('.rat').attr('data-id'),
				Description: $('textarea#textarea').val(),
			},
			function(data) {
			
			},
			'json'
		);
		var time = new Date();
		var month = ((time.getMonth()+1) > 9 ? (time.getMonth()+1) : "0" + (time.getMonth()+1));
		var day = ((time.getUTCDate()) > 9 ? (time.getUTCDate()) : "0" + (time.getUTCDate()));
		var date = (time.getFullYear()).toString().substring(2) + '-' + month + '-' + day + ' ' + time.getHours() + ':' + time.getMinutes() + ':' + time.getSeconds() + '.000';
		$('#comments_box .text').append('<div class="comment"><div class="kto"> <s:property value="email" /> </div><div class="kiedy">' + date + '</div><div class="tresc">' + $('textarea#textarea').val() + '</div></div>');
		$('textarea#textarea').val('');
	}
</script>


<div id="comments_box">
	<div class="text">
	
	<s:iterator value="comments" status="stat">
		<div class="comment">
			<div class="kto"><s:property value="comments [# stat.index][2].CloudLogin" /></div>
			<div class="kiedy"><s:property value="comments [# stat.index][0].CreatedAt" /></div>
			<div class="tresc"><s:property value="comments [# stat.index][0].Description" /></div>
		</div>
	</s:iterator> 
	</div>
	<textarea id="textarea" rows="2" cols="34"></textarea>
	<button id="dodaj" onclick="dodaj()" type="button">Dodaj</button> 
 
</div>