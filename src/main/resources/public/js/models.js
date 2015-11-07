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

            app = App(options);
            app.render();

            $('#new-group-info').on('click', function () {
                app.newGroupInfo();
            });

            $('#content').on('click', '.new-model', function () {
                var $this = $(this);
                var parent = $this.parents('.group-info');
                var index = parent.data('index');
                app.newModel(index);
            });
        });
    });

    function App(options) {
        var controls = options.controls;

        var data = [
            new GroupInfo(new Group(), [
                new Model(
                    new Professor(),
                    new Discipline(),
                    new LessonType()
                )
            ])
        ];

        function collectForm() {
            // todo: collect form into models
            return groups;
        }

        function render () {
            var view = utils.template.render("groups", { "data": data });
            controls.content.html(view);
        }

        function newModel (index) {
            var info = data[index];
            var inst = new Model(
                new Professor(),
                new Discipline(),
                new LessonType()
            );

            info.models.push(inst);

            var view = utils.template.render("model", inst);
            controls.content.find('.group-info[data-index="' + index + '"] .new-model-col').before(view);
        }

        function newGroupInfo () {

            var inst = new GroupInfo(new Group(), [
                new Model(
                    new Professor(),
                    new Discipline(),
                    new LessonType()
                )
            ]);

            data.push(inst);
            render();
        }

        return {
            collect: collectForm,
            render: render,
            newModel: newModel,
            newGroupInfo: newGroupInfo
        }
    }

    function GroupInfo(group, models) {
        this.group = group;
        this.models = models;
    }

    function Model(professor, discipline, lesson_type) {
        this.professor = professor;
        this.discipline = discipline;
        this.lesson_type = lesson_type;
    }

    function Group(id, alias) {
        this.id = id || 0;
        this.alias = alias || "";
    }

    Group.fromId = function (id) {
        if (typeof (id) !== 'number')
            return new Group();

        var tmp = bind("group", id);
        if (typeof (tmp) !== 'object')
            return new Group();

        return new Group(tmp.id, tmp.alias);
    };

    function Professor(id, first_name, last_name, middle_name) {
        this.id = id || 0;
        this.first_name = first_name || "";
        this.last_name = last_name || "";
        this.middle_name = middle_name || "";
    }

    Professor.fromId = function (id) {
        if (typeof (id) !== 'number')
            return new Professor();

        var tmp = bind("professor", id);
        if (typeof (tmp) !== 'object')
            return new Professor();

        return new Professor(
            tmp.id,
            tmp.first_name,
            tmp.last_name,
            tmp.middle_name
        );
    };

    function Discipline(id, alias) {
        this.id = id || 0;
        this.alias = alias || "";
    }

    Discipline.fromId = function (id) {
        if (typeof (id) !== 'number')
            return new Discipline();

        var tmp = bind("discipline", id);
        if (typeof (tmp) !== 'object')
            return new Discipline();

        return new Discipline(tmp.id, tmp.alias);
    };

    function LessonType(id, alias) {
        this.id = id || 0;
        this.alias = alias || "";
    }

    LessonType.fromId = function (id) {
        if (typeof (id) !== 'number')
            return new LessonType();

        var tmp = bind("lesson_type", id);
        if (typeof (tmp) !== 'object')
            return new LessonType();

        return new LessonType(tmp.id, tmp.alias);
    };

    function Auditory(id, alias, lat, lon, level, building_id) {
        this.id = id || 0;
        this.alias = alias || "";
        this.lat = lat || 0.0;
        this.lon = lon || 0.0;
        this.level = level || 0;
        this.building_id = building_id || 0;
    }

    Auditory.fromId = function (id) {
        if (typeof (id) !== 'number')
            return new Auditory();

        var tmp = bind("auditory", id);
        if (typeof (tmp) !== 'object')
            return new Auditory();

        return new Auditory(
            tmp.id,
            tmp.alias,
            tmp.lat,
            tmp.lon,
            tmp.level,
            tmp.building_id
        );
    };

    function bind(entity_name, id) {
        var entities = utils.entity.list(entity_name);
        return _.find(entities, function (item) {
            return item.id = id;
        })
    }

})(jQuery);


