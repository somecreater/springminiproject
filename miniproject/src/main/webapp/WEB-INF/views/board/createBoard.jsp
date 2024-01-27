<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시물 작성 페이지(테스트 단계)</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8sh+WyldQxFbSTFpCR78dt4vgLSF6g6yo"
          crossorigin="anonymous">
	<style>
	#fileUploadModal {
    top: 50%;
    transform: translateY(-50%);
    width: 1000px;
    height: 1300px;
    position: absolute;
    display: none;
    left: 30%;
    margin: 0;
  	justify-content: center;
  	align-items: center;
  	background-color: rgba(160, 160, 160, 1);
	}
	#fileUploadModal_box {
	
	}
	
	#filemodalbody{
	margin-left: 50px;
	margin-right: 50px;
    width: 900px;
    height: 800px;
    border: 2px solid #000;
    overflow-y: auto;
	}
	#filemodalbtn{
		margin-top: 80px;
		margin-bottom: 0px;
		margin-left: 250px;
		margin-right: 250px;
	}
	#fileupload_reset,#fileupload_tupload,#fileupload_close{
		align-items:center; 
		text-align: center;
		margin-left:10px;
		width: 150px;
		height: 50px;
		padding: 10px 5px;	
  		cursor: pointer;
	}
	</style>
</head>
<body>

<div class="insert_top">
	<h1><a href="listboard">메인 홈페이지로</a></h1>
	<h2>게시물 작성</h2>
	
	<form action="/board/saveBoard" method="post">
	<input id="_csrf" type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	<h4>게시판 선택</h4>
		<div class="insert_group">
			<!-- 여기에는 현재 존재하는 게시판 중 하나를 선택할 수 있게 한다 -->
			<select name="boardname">
				<c:forEach var="boardname" items="${boardlist}">
					<option value="${boardname}">${boardname}</option>
				</c:forEach>
			</select>
		</div>
	<h4>제목</h4>
		<div class="insert_group">
			<textarea id="title" name="title" rows="1" cols="100" required></textarea><br><br>
		</div>
		
	<h4>내용</h4>
		<div class="insert_group">
			<textarea id="content" name="content" rows="80" cols="140" required></textarea><br>
		</div>
	
	<h4>작성자의 이름</h4>
		<div class="insert_group">
			<input type="text" id="writer" name="writer" required><br>
		</div>		
		
		<button type="submit" class="btn_boardinsert">작성완료</button>
	</form>
	
	<h4>첨부파일 (다시 업로드 버튼을 누르면 기존의 파일 목록이 초기화됩니다!!)</h4>
		<div class="file_upload">
			<div class="file_upload_attach">
				<button type="button" id="fileupload_btn" data-bs-toggle="modal" data-bs-target="#fileUploadModal">파일 업로드</button>
				<input type="file" id="inputfile" name="upload" multiple>
			</div>
			<div class="file_upload_result">
			
			</div>
		</div>	
</div>

<!-- 파일 재등록 여부 확인 모달창 -->
<!-- 
<div class="modal fade" id="fileminimodal" tabindex="-1" role="dialog" aria-hidden="true">
	<div role="document">
		<div>
			<div>
			<h5>파일 등록 창을 엽니다.(파일을 이미 등록한 경우, 기존에 등록된 파일이 초기화 됩니다!!!!)</h5>
			</div>
			<div>
			<button></button>
			<button></button>
			</div>
		</div>
	</div>
</div>
-->
 
<!-- 파일등록 모달창 -->
<div class="modal fade" id="fileUploadModal" tabindex="-1" role="dialog" aria-labelledby="fileUploadModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-centered" id="fileUploadModal_box" role="document">
		<div class="file-modal-content">
			<div class="file-modal-header">
			<h5 class="file-modal-title" id="fileUploadModalLabel">파일을 드래그해서 업로드 해주세요</h5>
			</div>
			<div id="filemodalbody" class="file-modal-body">
			<!-- 파일 업로드 영역 -->
			
			</div>
			<h5 class="file-modal-title">파일을 드래그해서 업로드 해주세요</h5>
			<h5 id="fileuplaodrule">zip, js, exe, sh, alz 형태의 확장자를 가진 파일은 업로드가 제한됩니다!!!<br>이미지 파일은 따로 이미지 파일끼리 업로드 해주세요!!!</h5>
			<div id="filemodalresult">
			<!-- 파일 업로드 리스트 영역 -->
			
			</div>
			<div id="filemodalfooter">
			<div id="filemodalbtn" class="fileupload_btn_set">
				<button id="fileupload_reset">File reset</button>
				<button id="fileupload_tupload">File temp register</button>
				<button id="fileupload_close" data-dismiss="modal">exit</button>
			</div>
			</div>
		</div>
	</div>
</div>		
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"
        integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5PtkFExj5u9bOyDDn5a+3pu8L+I2LZ"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>        
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
        integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8sh+WyldQxFbSTFpCR78dt4vgLSF6g6yo"
        crossorigin="anonymous"></script>
<script>
$(document).ready(function(){
	var inputFile=$('#inputfile')
	var filemodalbtn=$('#fileupload_btn');
	var filemodal=$('#fileUploadModal');
	var filemodalclose=$('#fileupload_close');
	var filemodalreset=$('#fileupload_reset');
	
	filemodalbtn.click(function(){
		deleteshowfile();
		filemodal.modal('show');
	});
	
	filemodalreset.click(function(){
		deletefile();	
	});
	filemodalclose.click(function(){

		deletefile();
		filemodal.modal('hide');
	});
	
	const modal_file_drag=$('#filemodalbody');
	modal_file_drag.on("dragover",function(e){
	    console.log("파일 드래그 감지");
	    e.preventDefault();
	});
	
	modal_file_drag.on("drop",function(e){
		console.log("파일 드롭 완료");
	    e.preventDefault();
		var csrfToken = $("#_csrf").val();
		
		var formData = new FormData();
	    var files = e.originalEvent.dataTransfer.files;
	    for(var i=0;i<files.length;i++){
		    console.log(files[i].name);	
		    console.log(files[i].size);
		    formData.append("uploadFile",files[i]);
	    }
	    $.ajax({
	    	type: 'post',
			url:'/uploadFile',
			data: formData,
			dataType:'JSON',
	        beforeSend: function(xhr) {
	            xhr.setRequestHeader('X-CSRF-TOKEN', csrfToken);
	        },
            contentType: false,
            processData: false,
            success: function(response){
            	var resultresponse=response['result'];
				var resultfilelist=response['attachfilelist'];
				var thumbnailefilelist=response['thumbnaillist'];
				displayfilelist(resultresponse,resultfilelist,thumbnailefilelist);
            },
            error: function(error){
            	console.error('파일 업로드 에러!!');
            }
	    });
	    //여기서 드롭을 할때, 실제로 서버에 업로드 아래 result 화면에 순서대로 나열된 리스트 형식으로 보여주면 될듯(이미지+파일이름)
	    
	});
	
	inputFile.change(function(){
		var formData = new FormData();
		var files = $('#inputfile')[0].files;
		$.ajax({
			type: 'post',
			url:'/upload',
			data: formData,
            contentType: false,
            processData: false,
			success: function(response){
				var resultfilelist=response['attachfilelist'];
				displayfilelist(resultfilelist);
			},
			error: function(error){
				console.error('파일 업로드 에러!!');
			}
		});
	});
	
	function deletefile(){
		
		
	}
	function deleteshowfile(){
		
	}
	
	function displayfilelist(resultresponse,resultfilelist,thumbnailefilelist){
		var filemodalresult=$('#filemodalresult');
		var str="";

		filemodalresult.empty();
		
		if(resultresponse === 'upload_fail' || resultfilelist === null)
		{
			console.log('지원하지 않는 파일');
			str=str+'<div><p>업로드에 실패하였습니다. 다시 시도해주세요</p></div>';	
		}
		else if(resultfilelist[0].image===true)
		{
			$.each(resultfilelist,function(index,file){
				//이미지 파일 리스트인 경우	
			});
		}
		else
		{
			$.each(resultfilelist,function(index,file){
				//이미지 파일 리스트가 아닌 경우	
			});
		}
		
		
		filemodalresult.append(str);
	}
});

</script>
</body>
</html>