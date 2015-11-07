var app = null;

(function ($) {

    'use strict';

    $(function () {
        var options = {
            controls: {
                content: $('#content')
            }
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

            $('#new-group-info').on('click', function () {
                app.newGroupInfo();
            });

            $('#content').on('click', '.new-model', function () {
                var container = $(this).parent();
                app.newModel(container);
            });
        });
    });

    function App(options) {

        var controls = options.controls;

        this.collect = function () {
            // todo: collect form into models
            return groups;
        };

        this.newModel = function (contaner) {
            var view = utils.template.render("model", new GroupCurriculum());
            contaner.before(view);
        };

        this.newGroupInfo = function () {
            var view = utils.template.render("groups", { "data" : [ new GroupInfo() ] });
            controls.content.append(view);
        };

        this.newGroupInfo();
    }

})(jQuery);


