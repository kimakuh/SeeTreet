var slide = null;
var mAritstApplication = null;
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
		
		slide = new ProviderView();
		slide.create($(".listtab.provider"), function() {
			$(slide.getId()).bind("click" , slide.showCreateModal);
		});		
		
		
		getProviderContents( 1 , function(data , state , res) {			
			contents = data.data;
			for(var i in contents) {
				$(slide.getId()).unbind("click");
				
				var content = contents[i];
				slide.setContent(content);
				for(var j in content.artists) {
					if(content.isConfirmed_artistId == content.artists[j]._id) 
						slide.addArtist(content.artists[j] , true);
					else slide.addArtist(content.artists[j] , false);
				}
				slide = new ProviderView();
				slide.create($(".listtab.provider"));
				$(slide.getId()).bind("click" , slide.showCreateModal);
			}				
		});
				
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
		
		mArtistApplication = new ArtistApplication();
		mArtistApplication.create($(".listtab.artist") , function() {
			getUser(function(data , state) {
				if(state == "success") {
					var artistId = data.data.user_artist._id;	
					var page = mArtistApplication.getPage();
					getArtistApplications(artistId, page, function(res , state2) {
						if(state2 == "success") {
							console.log(res);
						}else {
							
						}
					});					
				} else {
					
				}				
			})
		});
		
		getArtistRecommends(mArtistApplication.getRecPage() , function(data ,state) {
			if(state == "success") {
				console.log(data);
			}else {
				
			}
		})
		
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
		create : function(target , callback) {
			var str = '<div class="content_container p_content_container" data-id="empty" data-state="init">'+													
					  '</div>';		
			target.prepend(str);		
			
			hlist = new HSlider();
			isInit = true;
			
			setTimeout(function() {
				$(".content_container").removeAttr("data-state");
				if(callback != undefined)callback();
			},300);
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
				desc : content.contentStartTime + " ~ " + content.contentEndTime,
				id : contentId,
				isConfirmed : null
			}));
			
			print(content.provider.providerImage[0]);
			$(id+ " .identity.box").css({"background-image" : 'url('+content.provider.providerImage[0]+')'});
			
			if(hlist.isInited() == false) hlist.create($(id) , contentId , true);
		}, 
		getId : function() {
			return id; 
		},
		showCreateModal : function() {			
			$("#provider_create_content .modal-content").load("./views/modal/modal_create_content.html", function() {				
				$("#provider_create_content").modal({
					backdrop : true,
					keyboard : true,
					show	 : true
				})
				$("#provider_create_content").off("hidden.bs.modal");
				$("#provider_create_content").on("hidden.bs.modal" , function() {
					$("#provider_create_content .modal-content *").remove();
				});
			});
		}
	};
};

var ArtistApplication = function() {
	var id = "";
	var hlist = null;
	var mPage = 1;
	var mRecPage = 1;
	return {
		create : function(target , callback) {
			var str = '<div class="content_container a_content_container" data-id="empty" data-state="init">'
					+ '</div>';
			target.prepend(str);
			
			hlist = new HSlider();			

			setTimeout(function() {
				$(".a_content_container").removeAttr("data-state");
				if (callback != undefined)
					callback();
			}, 300);
		} , 
		addProvider : function( pData , isPassed) {
			
		} , 
		getPage : function() {
			return mPage;
		} , 
		setPage : function(page) {
			mPage = page;
		} ,
		getRecPage : function() {
			return mRecPage;
		} ,
		setRecPage : function(page) {
			mRecPage = page;
		},
		addRecProvider : function ( pData ) {
			
		}
	};
}


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
		var image = info.image;
		box_str += '<div class="identity box" data-id="'+id+'">'+
						'<div class="image" ></div>'+
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
		var image = info.image;
		box_str += '<div class="identity box" data-id="'+id+'">'+
						'<div class="image" ></div>'+
						'<div class="title">'+title+'</div>'+
						'<div class="option descript">'+desc+'</div>'+
						'<div class="box_hover"></div>'+
					'</div>';
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
			if (isArtist) {
				var artist = BoxFactory.create(BoxFactory.KEY_CREATE_ARTIST, {
					title : data.name,
					desc : data.description,
					id : data._id,
					confirm : isConfirmed
				});
				$(mId + " .viewport").append(artist);
				$(mId + " .viewport .artist.box[data-id='" + data._id + "']")
						.css("background-image","url('" + data.artistImages[0] + "')");
				mMap[data._id] = mId + " .viewport .artist.box[data-id='"+ data._id + "']";

				$(mId).removeClass("empty");
			} else {
				var provider = BoxFactory.create(BoxFactory.KEY_CREATE_PROVIDER, {
					title : data.name,
					desc : data.description , 
					id : data._id,
					confirm : isConfirmed
				});
				$(mId + " .viewport").append(provider);
				$(mId + " .viewport .provider.box[data-id='" + data._id + "']")
						.css("background-image","url('" + data.providerImage[0] + "')");
				mMap[data._id] = mId + " .viewport .provider.box[data-id='"+ data._id + "']";

				$(mId).removeClass("empty");
			}	
			
		},
		getData : function(data) {
			print(mMap);
		},		
		create : function(target , id , isArtistList) {
			mId += "[data-id='"+id+"']";
			isArtist = isArtistList;
			if(isArtist) {
				target.append(
						'<div class="listview empty" data-id="'+id+'">'+
							'<h2 class="p_empty_text">지원자가 없다.</h2>'+
							'<div class="prev_btn list_btn"></div>'+
							'<div class="next_btn list_btn"></div>'+
							'<div class="viewport"></div>'+
						'</div>'
				);
			} else {
				
			}
			
			
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




