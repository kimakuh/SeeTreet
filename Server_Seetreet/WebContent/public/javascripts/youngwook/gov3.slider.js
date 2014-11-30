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
			console.log(data);
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
					mArtistApplication.initHSlide(artistId);
					getArtistApplications(artistId, page, function(res , state2) {
						print(artistId + " , " + page);
						if(state2 == "success") {
							var arr = res.data;
							for(var i in arr) {
								if(arr[i].isConfirmed_artistId == artistId) {
									mArtistApplication.addProvider(arr[i], true);
								} else {
									mArtistApplication.addProvider(arr[i], false);
								}
							}							
						}else {
							
						}
					});					
				} else {
					
				}				
			})
		});
		
		getArtistRecommends(mArtistApplication.getRecPage() , function(data ,state) {
			if(state == "success") {
				var arr = data.data;
				for(var i in arr) {
					mArtistApplication.addRecProvider($(".listtab.artist") , arr[i]);
				}				
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
			callback();
			setTimeout(function() {
				$(".a_content_container").removeAttr("data-state");					
			}, 300);
		} , 
		addProvider : function( pData , isPassed) {
			hlist.setData(pData , isPassed);
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
		addRecProvider : function ( target,  pData ) {
			target.append(BoxFactory.create(BoxFactory.KEY_CREATE_PROVIDER , {
				title : pData.contentTitle,
				desc : pData.provider.description,
				id : pData._id,
				confirm : false
			}));
			
			$(".listtab.artist .provider.box[data-id='"+pData._id+"']").css("background-image" , "url('"+pData.provider.providerImage[0]+"')");
			$(".listtab.artist .provider.box[data-id='"+pData._id+"'] .option.btn_apply").unbind("click");
			$(".listtab.artist .provider.box[data-id='"+pData._id+"'] .option.btn_apply").bind("click" , function() {								
				mArtistApplication.applyContent(pData._id , pData.provider._id);
			});
			$(".listtab.artist .provider.box[data-id='"+pData._id+"'] .box_hover").unbind("click");
			$(".listtab.artist .provider.box[data-id='"+pData._id+"'] .box_hover").bind("click" , function() {
				modal_Factory.providerModal.getProviderInfo(pData.provider._id);
			});
		},
		applyContent : function(id , providerid) {		
			postApplication(id, function(result , state) {				
				if(state == "success") {
					var title = $(".listtab.artist .provider.box[data-id='"+id+"'] .title").text();
					var url = $(".listtab.artist .provider.box[data-id='"+id+"']").css("background-image");
					$(".listtab.artist .provider.box[data-id='"+id+"']").remove();
					mArtistApplication.addProvider({
						_id : id,
						contentTitle : title ,
						description : "" ,
						provider : {_id : providerid} ,
						providerImage : url,
						isFinished : false
					} , false);
				}
			});			
		} , 
		initHSlide : function(artistId) {
			hlist.create($(".content_container.a_content_container") , artistId , false);
		} 
	};
}


var BoxFactory = {};
BoxFactory.KEY_CREATE_CONTENT = 0;
BoxFactory.KEY_CREATE_ARTIST = 1;
BoxFactory.KEY_CREATE_PROVIDER = 2;
BoxFactory.KEY_CREATE_HISTORY = 3;
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
		box_str += '<div class="provider box" data-id="'+id+'">'+
						'<div class="image" ></div>'+
						'<div class="title">'+title+'</div>'+
						'<div class="option btn_apply '+(isConfirmed?'passed':'')+'">지원하기</div>'+
						'<div class="box_hover"></div>'+
					'</div>';
	break;
	case BoxFactory.KEY_CREATE_HISTORY :
		var isFinished = info.isFinished;
		box_str += '<div class="provider box" data-id="'+id+'">'+
						'<div class="image" ></div>'+
						'<div class="title">'+title+'</div>'+
						'<div class="option btn_apply '+(isConfirmed?'passed':'')+'">'+(isFinished?'종료된 공연' : isConfirmed? '승인됨' : '대기중')+'</div>'+
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
				$(mId + " .viewport .artist.box[data-id='" + data._id + "'] .box_hover").click(function() {
					console.log(data._id);
					modal_Factory.artistModal.getArtistInfo(data._id);
				});
				
				$(mId + " .viewport .artist.box[data-id='" + data._id + "'] .option.btn_permit").click(function() {
					var contentId = $(mId).attr("data-id");
					permitArtist(data._id, contentId, function(data, state) {
						console.log(data);
						if(state == "success") {
							$(mId + " .viewport .confirm.artist.box").removeClass("confirm");
							$(mId + " .viewport .artist.box[data-id='" + data._id + "']").addClass("confirm");
						}						
					});
				});
				
				mMap[data._id] = mId + " .viewport .artist.box[data-id='"+ data._id + "']";				
				$(mId).removeClass("empty");
			} else {	
				var provider = BoxFactory.create(BoxFactory.KEY_CREATE_HISTORY, {
					title : data.contentTitle,
					desc : data.contentStartTime + " ~ " +data.contentEndTime , 
					id : data._id,
					confirm : isConfirmed,
					isFinished : data.isFinished
				});
				
				$(mId + " .viewport").append(provider);
				$(mId + " .viewport .provider.box[data-id='" + data._id + "']")
						.css("background-image",(data.providerImage||"url('"+data.provider.providerImage[0]+"')"));
		
				mMap[data._id] = mId + " .viewport .provider.box[data-id='"+ data._id + "']";
				$(mId + " .viewport .provider.box[data-id='" + data._id + "'] .box_hover").click(function() {
					modal_Factory.providerModal.getProviderInfo(data.provider._id);
				});	
				
				if(isConfirmed) {
					$(mId + " .viewport .provider.box[data-id='" + data._id + "'] .option.passed").click(function() {
						postConfirmFromArtist({
							category : $("select.main-category option:selected").text(),
							detailGenre : $("select.sub-category option:selected").text() 
						}, data._id, function(res , state) {
							$(mId + " .viewport .provider.box[data-id='" + data._id + "'] .option").text("완료");
						});
					});	
				}
				
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
				target.append(
						'<div class="listview empty" data-id="'+id+'">'+
							'<h2 class="p_empty_text">어서 지원해봐!</h2>'+
							'<div class="prev_btn list_btn"></div>'+
							'<div class="next_btn list_btn"></div>'+
							'<div class="viewport"></div>'+
						'</div>'
				);
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




