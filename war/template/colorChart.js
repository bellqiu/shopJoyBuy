jq(document).ready(function(){
			list = jq(".cbr li");
			list.bind("click", function(event){
					parentElement = event.target.parentNode.parentNode.parentNode.parentNode;
					chartName = parentElement.querySelector(".cbt").children[0].name
						jq("#cbl" + chartName.toLowerCase() + " img")[0].src=event.target.src;
						jq("#cbl" + chartName.toLowerCase() + " span")[0].innerHTML = event.currentTarget.childNodes[1].innerHTML;
				});
		});