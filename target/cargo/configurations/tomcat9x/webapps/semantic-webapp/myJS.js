function doQuery()
{
//alert('doQuery...');	
	if((document.getElementById('repoURL').value!='')&&(document.getElementById('repoId').value!='')&&(document.getElementById('sparqlQuery').value!=''))
	{
		var q_str = 'reqType=doQuery';

		q_str = q_str+'&repoURL='+document.getElementById('repoURL').value;
		q_str = q_str+'&repoId='+document.getElementById('repoId').value;
		q_str = q_str+'&sparqlQuery='+document.getElementById('sparqlQuery').value;
		doAjax('Logging',q_str,'doQuery_back','post',0);
	}else
	{
		alert('Please, fill all the search fields...');
	}
}

function doQuery_back(result)
{
alert('result:'+result);
}





