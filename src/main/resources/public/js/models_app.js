var app = null;

(function ($) {

    'use strict';

    $(function () {
        var content = $('#content');

        var options = {
            controls: {
                content: content
            }
        };

        $.when(
            utils.template.init("group_info"),
            utils.template.init("group_model"),
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

            $('#next-step').on('click', function () {
                app.nextStep();
            });

            content.on('click', '.new-model', function () {
                var container = $(this).parents('.new-model-container');
                app.newModel(container);
            });

            content.on('click', '.group-model-remove', function () {
                var container = $(this).parents('.group-model');
                app.removeModel(container);
            });

            content.on('click', '.group-info-remove', function () {
                var container = $(this).parents('.group-info');
                app.removeGroupInfo(container);
            });
        });
    });

    function App(options) {

        var controls = options.controls;
        var self = this;

        self.collect = function () {
            var res = [];

            $('.group-info').each(function () {
                var $this = $(this);
                var group_alias = $this.find('.group').val();
                var group = utils.entity.bind('group', 'alias', group_alias);

                // todo: add validation

                var base_unit = {
                    'group_id': group.id,
                    'discipline_id': 0,
                    'professor_id': 0,
                    'lesson_type_id': 0
                };

                $this.find('.group-model').each(function () {
                    var $this = $(this);

                    // todo: add binding by fio
                    var professor_last_name = $this.find('.professor').val();
                    var professor = utils.entity.bind('professor', 'last_name', professor_last_name);

                    var discipline_alias = $this.find('.discipline').val();
                    var discipline = utils.entity.bind('discipline', 'alias', discipline_alias);

                    var lesson_type_id = $this.find('.lesson-type').val();
                    var lesson_type = utils.entity.bind('lesson_type', 'id', lesson_type_id);

                    var unit = $.extend({}, base_unit, {
                        professor_id: professor.id,
                        discipline_id: discipline.id,
                        lesson_type_id: lesson_type.id
                    });

                    res.push(unit);
                });
            });

            return res;
        };

        self.newModel = function (contaner) {
            var view = utils.template.render("group_model", new GroupCurriculum());
            contaner.before(view);
        };

        self.newGroupInfo = function () {
            var view = utils.template.render("group_info", new GroupInfo());
            controls.content.append(view);
        };

        self.nextStep = function () {
            $.ajax({
                url: '/input/models',
                method: 'POST',
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                data: JSON.stringify(self.collect()),
                async: true
            }).then(function (resp) {

                window.location = '/input/' + resp.data + '/boundaries';

            });
        };

        self.removeModel = function (container) {
            container.remove();
        };

        self.removeGroupInfo = function (container) {
            container.remove();
        };

        self.newGroupInfo();
    }

})(jQuery);


