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
            utils.template.init("bound_info"),
            utils.entity.initFromController('config', window.location.pathname)
        ).then(function () {

                app = new App(options);

                $('#new-group-info').on('click', function () {
                    app.newGroupInfo();
                });

                $('#next-step').on('click', function () {
                    app.nextStep();
                });

                $('#content').on('click', '.new-model', function () {
                    var container = $(this).parent();
                    app.newModel(container);
                });
            });
    });

    function App(options) {

        var controls = options.controls;
        var self = this;

        self.collect = function () {
            // todo: collect form into models
            return groups;
        };

        self.newModel = function (contaner) {
            var view = utils.template.render("model", new GroupCurriculum());
            contaner.before(view);
        };

        self.newGroupInfo = function () {
            var view = utils.template.render("groups", { "data" : [ new GroupInfo() ] });
            controls.content.append(view);
        };

        self.nextStep = function () {
            $.ajax({
                url: '/input/steps/models',
                method: 'POST',
                data: JSON.stringify(self.collect()),
                async: true
            }).then(function (id) {
                window.location = '/input/' + id + '/steps/boundaries';
            });
        };

        self.newGroupInfo();
    }

})(jQuery);


