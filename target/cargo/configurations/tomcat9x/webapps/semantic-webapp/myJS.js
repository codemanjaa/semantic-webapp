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
	 jsonData = JSON.parse(result);
	   document.getElementById("content").innerHTML= result; //'<object type="text/html" data=jsonData ></object>';
	  // Create the table element
	  console.log(result);
         let table = document.createElement("table");
           table.style.border = "1px solid #000";
         let div = document.createElement("div");
      	 let cols = Object.keys(jsonData["results"]); 
//alert('result:'+result);
}





