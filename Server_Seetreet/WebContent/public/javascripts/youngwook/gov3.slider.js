var Listtab = {};
Listtab.TAB_PROVIDER = 0;
Listtab.TAB_ARTIST = 1;
Listtab.show = function(STATE) {
	$(".listtab.provider *").remove();
	$(".listtab.artist *").remove();
	$("#provider_create_content").remove();
	$("#artist_apply_content").remove();
	switch(STATE) {
	case Listtab.TAB_PROVIDER :
		
		$("body").append(
			'<div class="modal fade" id="provider_create_content" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">' +
				'<div class="modal-dialog">' +
					'<div class="modal-content">' +
					'</div>' +
				'</div>' +
			'</div>'
		);	
		
		var slide = new ProviderView();
		slide.create($(".listtab.provider"));
		$(slide.getId).bind("click" , slide.showCreateModal);
		
		getProviderContents(function(aa , state , res) {
			contents = dummy;
			for(var i in contents) {
				$(slide.getId).unbind("click");
				
				var content = contents[i];
				slide.setContent(content);
				for(var j in content.artists) {
					if(content.isConfirmed_artistId == content.artists[j]._id) 
						slide.addArtist(content.artists[j] , true);
					else slide.addArtist(content.artists[j] , false);
				}
				slide = new ProviderView();
				slide.create($(".listtab.provider"));
			}				
		});
		
		setTimeout(function() {
			$(".p_content_container").removeAttr("data-state");
		},300);
		break;
	case Listtab.TAB_ARTIST:
		$("body").append(
				'<div class="modal fade" id="artist_apply_content" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">' +
					'<div class="modal-dialog">' +
						'<div class="modal-content">' +
						'</div>' +
					'</div>' +
				'</div>'
			);
		
		break;
	}
};

var ProviderView = function() {
	var hlist;
	var contentBox;
	var tag;
	var isInit = false;
	var id = ".p_content_container[data-id='empty']";
	var contentId = "";
	return {
		create : function(target) {
			var str = '<div class="p_content_container" data-id="empty" data-state="init">'+													
					  '</div>';		
			target.prepend(str);		
			
			hlist = new HSlider();
			isInit = true;
		},
		addArtist : function(artist , isConfirmed) {
			if(isInit == false) {
				print("not inited");
				return;
			}			
			hlist.setData(artist , isConfirmed);			
		},
		setContent : function(content) {
			if(isInit == false) {
				print("not inited");
				return;
			}
			contentId = content._id;
			
			$(id).attr("data-id", contentId);
			id = ".p_content_container[data-id='"+contentId+"']";
							
			$(id).append(BoxFactory.create(BoxFactory.KEY_CREATE_CONTENT , {
				title : content.contentTitle,
				desc : content.description,
				id : contentId,
				isConfirmed : null
			}));
			
			if(hlist.isInited() == false) hlist.create($(id) , contentId , true);
		}, 
		getId : function() {
			return id; 
		},
		showCreateModal : function() {
			print(">>> " + id);
			$("#provider_create_content .modal-content").load("./views/modal/modal_create_content.html");
		}
	};
};


var BoxFactory = {};
BoxFactory.KEY_CREATE_CONTENT = 0;
BoxFactory.KEY_CREATE_ARTIST = 1;
BoxFactory.KEY_CREATE_PROVIDER = 2;

BoxFactory.create = function(key , info) {
	var box_str = "";
	var title = info.title;
	var desc  = info.desc;
	var id 	  = info.id;
	var isConfirmed = info.confirm;
		
	switch(key) {
	case BoxFactory.KEY_CREATE_CONTENT :
		box_str += '<div class="identity box" data-id="'+id+'">'+
						'<div class="image"></div>'+
						'<div class="title">'+title+'</div>'+
						'<div class="option descript">'+desc+'</div>'+
						'<div class="box_hover"></div>'+
					'</div>';			
	break;
	case BoxFactory.KEY_CREATE_ARTIST :	
		box_str += '<div class="artist box '+(isConfirmed?"confirm":"")+'" data-id="'+id+'">'+
						'<div class="image"></div>'+
						'<div class="title">'+title+'</div>'+
						'<div class="option btn_permit"></div>'+
						'<div class="box_hover"><h3><strong>'+title+'</strong>은 누구일까요?<br/> 클릭해봐요!</h3></div>'+
					'</div>';	
	break;
	case BoxFactory.KEY_CREATE_PROVIDER :		
	break;
	}	
	
	return box_str;
};

var HSlider = function() {
	var mMap = {};
	var didInit = false;
	var mId = ".listview";
	var isArtist = true;
	
	return {
		setData : function(data , isConfirmed) {
			if(!didInit) { 
				print("Did not init");
				return;
			}
						
			var artist = BoxFactory.create(BoxFactory.KEY_CREATE_ARTIST ,
			{
				title : data.name,
				desc : data.description,				
				id : data._id,
				confirm : isConfirmed
			});			
			$(mId+ " .viewport").append(artist);			
			$(mId+ " .viewport .artist.box[data-id='"+data._id+"']")
				.css("background-image" , "url('"+data.artistImages[0]+"')");				
			mMap[data._id] = mId+ " .viewport .artist.box[data-id='"+data._id+"']";
			
			$(mId).removeClass("empty");
		},
		getData : function(data) {
			print(mMap);
		},		
		create : function(target , id , isArtistList) {
			mId += "[data-id='"+id+"']";
			isArtist = isArtistList;
			target.append(
				'<div class="listview empty" data-id="'+id+'">'+
					'<h2 class="p_empty_text">지원자가 없다.</h2>'+
					'<div class="prev_btn list_btn"></div>'+
					'<div class="next_btn list_btn"></div>'+
					'<div class="viewport"></div>'+
				'</div>'
			);
			
			$(mId + " .prev_btn").click(function(e) {				
				$(mId + " .viewport").animate({
					scrollLeft : "-=440px"
				}, {
					"duration" 	: 500,
					"easing"	: "easeInOutExpo" ,
					"complete"	: function() {
						
					}
				});
			});
			
			$(mId + " .next_btn").click(function(e) {				
				$(mId + " .viewport").animate({
					scrollLeft : "+=440px"
				}, {
					"duration" 	: 500,
					"easing"	: "easeInOutExpo" ,
					"complete"	: function() {
						
					}
				});
			});
			
			didInit = true;
		},
		isInited : function() {
			return didInit;
		}
	};
};





function print(string) {
	console.log(">>>>>>>print<<<<<<<");
	console.log(string);
}




