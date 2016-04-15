/**
 * Created by wannabe on 07.11.15.
 */
var app = null;

(function ($) {

	'use strict';

	$(function () {
		var options = {
			schedule_id: $("#schedule_id").val()
		};

		$.when(
			utils.entity.init("group"),
			utils.entity.init("auditory"),
			utils.entity.init("professor"),
			utils.entity.init("lesson_type"),
			utils.entity.init("discipline")
		).then(function () {

			app = new App(options);
			setInterval(function () {
				app.status($("#progress"), $("#time"));
			}, 700);
		});
	});

	function App(options) {


		this.progress = function (status, $progressBar) {
			var diff;
			if (!status.maxFitness && !status.finished) {
				$progressBar.css({"background-color": "rgba(255, 0, 0, 1)"});
			} else {
				diff = status.fitness / status.maxFitness;
				if (diff > 0.5) {
					$progressBar.css({"background-color": "rgba(255, " + (parseInt(diff * 510 - 255)) + ", 0, 1)"});
				} else {
					$progressBar.css({"background-color": "rgba(" + (parseInt(diff * 510)) + ", 255, 0, 1)"});
				}
			}

			if (status.finished) {
				$progressBar.css({width: "100%"});
				$progressBar.text("100%");
			} else {
				$progressBar.css({width: status.progress * 100 + "%"});
				$progressBar.text((status.progress * 100 + "").substring(0, 2) + "%");
			}
		};

		this.time = function (status, $time) {
			if (isFinite(status.remaningTime)) {
				var minutes = parseInt(status.remaningTime / 60, 10);
				var seconds = parseInt(status.remaningTime - minutes * 60, 10);
				if (seconds < 10) {
					seconds = "0" + seconds;
				}
				$time.text(minutes + ":" + seconds);
			}
		};

		this.status = function ($progressBar, $time) {
			var self = this;
			$.ajax({
				type: 'GET',
				url: '/status/' + options.schedule_id + '/data',
				async: false
			}).done(function (e) {
				if (e.data != null) {
					self.progress(new Status(e.data.finished, e.data.progress, e.data.currentFitness, e.data.maxFitness, e.data.remaningTime), $progressBar);
					self.time(new Status(e.data.finished, e.data.progress, e.data.currentFitness, e.data.maxFitness, e.data.remaningTime), $time);
					if (e.data.finished) {
						window.location.replace("/output/" + options.schedule_id);
					}
				}
			});
		};
	}

})(jQuery);


