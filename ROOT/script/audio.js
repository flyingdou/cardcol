var _audio = {
	_audio_obj : null,
	_audio_val : true,
	_button : "",
	_init : function(btnId) {
		var options_audio = {
			loop : "loop",
			preload : "auto",
			autoplay : true,
			src : $('.u-audio').attr('src')
		}
		_audio._button = btnId;
		_audio._audio_obj = new Audio();
		for ( var key in options_audio) {
			_audio._audio_obj[key] = options_audio[key];
		}
		// console.log(_audio._audio_obj);
		_audio._audio_obj.load();
	},
	audio_play : function() {
		if (_audio._audio_val === true) {
			_audio._audio_val = false;
			$('#' + _audio._button).val("播放");
			if (_audio._audio_obj)
				_audio._audio_obj.pause();
		} else {
			_audio._audio_val = true;
			$('#' + _audio._button).val("停止");
			if (_audio._audio_obj)
				_audio._audio_obj.play();
		}
	},
	audio_stop : function() {
		_audio._audio_val = false;
		if (_audio._audio_obj)
			_audio._audio_obj.pause();
	}
}
