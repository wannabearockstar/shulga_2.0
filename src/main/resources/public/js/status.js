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
            utils.template.init("groups"),
            utils.template.init("model"),
            utils.entity.init("group"),
            utils.entity.init("auditory"),
            utils.entity.init("professor"),
            utils.entity.init("lesson_type"),
            utils.entity.init("discipline")
        ).then(function () {

                app = new App(options);
                setInterval(function () {
                    app.status($("#progress"));
                }, 1000);
            });
    });

    function App(options) {


        this.progress = function (status, $progressBar) {
            if (status.fitness > 255) {
                $progressBar.css({"background-color": "rgba(255, " + (255 - status.fitness) + ", 0, 1)"});
            } else {
                $progressBar.css({"background-color": "rgba("+status.fitness+", 255, 0, 1)"});
            }

            $progressBar.css({width: status.progress * 100 + "%"});
            $progressBar.text(status.progress * 100 + "%");
        };

        this.status = function ($progressBar) {
            var self = this;
            $.ajax({
                type: 'GET',
                url: '/status/' + options.schedule_id + '/data',
                async: false
            }).done(function (e) {
                if (e.data != null) {
                    self.progress(new Status(e.data.finished, e.data.progress, e.data.currentFitness), $progressBar);
                    if (e.data.finished) {
                        console.log("finished");
                    }
                }
            });
        };
    }

})(jQuery);


