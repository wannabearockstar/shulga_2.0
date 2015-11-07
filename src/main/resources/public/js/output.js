/**
 * Created by wannabe on 07.11.15.
 */
var app = null;

(function ($) {

    'use strict';

    $(function () {
        var options = {
        };

        $.when(
            utils.entity.init("group"),
            utils.entity.init("auditory"),
            utils.entity.init("professor"),
            utils.entity.init("lesson_type"),
            utils.entity.init("discipline")
        ).then(function () {
                app = new App(options);
                $("#content").html(app.getTable($("#csv_data").html()));
            });
    });

    function App() {
        this.getTable = function(data) {
            var lines = data.split("\n"),
                output = [],
                i;
            for (i = 0; i < lines.length; i++)
                output.push("<tr><td>"
                + lines[i].slice(0,-1).split(",").join("</td><td>")
                + "</td></tr>");
            output = "<table class='table table-bordered'>" + output.join("") + "</table>";
            return output;
        }
    }

})(jQuery);


