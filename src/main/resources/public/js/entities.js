function GroupInfo(group, curriculum) {
    this.group = group || new Group();
    this.curriculum = curriculum || [ new GroupCurriculum() ];
}

GroupInfo.fromSchedulerConfig = function (config) {
    if (typeof (config) !== 'object')
        return [ new GroupInfo() ];

    if (typeof (config['curriculum']) === 'undefined')
        return [ new GroupInfo() ];

    var group = _.groupBy(config['curriculum'], 'group_id');

    return _.map(group, function (units, group_id) {

        var group = Group.fromId(group_id);

        var curriculum = _.map(units, function (unit) {
            return new GroupCurriculum(
                Professor.fromId(unit.professor_id),
                Discipline.fromId(unit.discipline_id),
                LessonType.fromId(unit.lesson_type_id)
            )
        });

        return new GroupInfo(group, curriculum);
    });
};

function GroupCurriculum(professor, discipline, lesson_type) {
    this.professor = professor || new Professor();
    this.discipline = discipline || new Discipline();
    this.lesson_type = lesson_type || new LessonType();
}

function Group(id, alias) {
    this.id = id || 0;
    this.alias = alias || "";
}

Group.fromId = function (id) {
    var type = typeof (id);

    if (type !== 'number' && type !== 'string')
        return new Group();

    id = parseInt(id);
    var tmp = utils.entity.bind("group", 'id', id);

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
    var type = typeof (id);

    if (type !== 'number' && type !== 'string')
        return new Professor();

    id = parseInt(id);
    var tmp = utils.entity.bind("professor", "id", id);

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
    var type = typeof (id);

    if (type !== 'number' && type !== 'string')
        return new Discipline();

    id = parseInt(id);

    var tmp = utils.entity.bind("discipline", "id", id);

    if (typeof (tmp) !== 'object')
        return new Discipline();

    return new Discipline(tmp.id, tmp.alias);
};

function LessonType(id, alias) {
    this.id = id || 0;
    this.alias = alias || "";
}

LessonType.fromId = function (id) {
    var type = typeof (id);

    if (type !== 'number' && type !== 'string')
        return new LessonType();

    id = parseInt(id);
    var tmp = utils.entity.bind("lesson_type", "id", id);

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
    var type = typeof (id);

    if (type !== 'number' && type !== 'string')
        return new Auditory();

    id = parseInt(id);

    var tmp = utils.entity.bind("auditory", "id", id);

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

function Status(finished, progress, fitness, maxFitness, remaningTime) {
    this.finished = finished || false;
    this.progress = progress || 0.0;
    this.fitness = fitness || 0.0;
    this.maxFitness = maxFitness || false;
    this.remaningTime = remaningTime || 0;
}

function WeekDay(id, alias) {
    this.id = id || 1;
    this.alias = alias || "";
}

WeekDay.fromId = function (id) {
    switch (id) {
        case 1: return new WeekDay(id, 'Понедельник');
        case 2: return new WeekDay(id, 'Вторник');
        case 3: return new WeekDay(id, 'Среда');
        case 4: return new WeekDay(id, 'Четверг');
        case 5: return new WeekDay(id, 'Пятница');
        case 6: return new WeekDay(id, 'Суббота');
        case 7: return new WeekDay(id, 'Воскресенье');

        default: return new WeekDay(0, 'Неопределено');
    }
};

function DayTime(id, alias) {
    this.id = id || 0;
    this.alias = alias || "";
}

DayTime.fromId = function (id) {
    var type = typeof (id);

    if (type !== 'number' && type !== 'string')
        return new DayTime();

    id = parseInt(id);
    var tmp = utils.entity.bind("time", "id", id);

    if (typeof (tmp) !== 'object')
        return new DayTime();

    return new DayTime(tmp.id, tmp.alias);
};

function AuditoryTimesBound(auditory, days, times) {
    this.auditory = auditory || new Auditory();
    this.days = days || [ new WeekDay() ];
    this.times = times || [ new DayTime() ];
}

AuditoryTimesBound.fromSchedulerConfig = function (config) {
    if (typeof (config) !== 'object')
        return [ new AuditoryTimesBound() ];

    if (typeof (config['bounds']) === 'undefined' ||
        typeof (config['bounds']['auditory_times']) === 'undefined') {
        return [ new AuditoryTimesBound() ];
    }

    return _.map(config['bounds']['auditory_times'], function (bound, auditory_id) {

        var auditory = Auditory.fromId(auditory_id);

        var week_days = _.map(bound['days'], function (day_id) {
            return WeekDay.fromId(day_id);
        });

        var times = _.map(bound['times'], function (time_id) {
            return DayTime.fromId(time_id);
        });

        return new AuditoryTimesBound(auditory, week_days, times);
    });
};

function DisciplineTimesBound(discipline, days, times) {
    this.discipline = discipline || new Discipline();
    this.days = days || [ new WeekDay() ];
    this.times = times || [ new DayTime() ];
}

DisciplineTimesBound.fromSchedulerConfig = function (config) {
    if (typeof (config) !== 'object')
        return [ new DisciplineTimesBound() ];

    if (typeof (config['bounds']) === 'undefined' ||
        typeof (config['bounds']['discipline_times']) === 'undefined') {
        return [ new DisciplineTimesBound() ];
    }

    return _.map(config['bounds']['discipline_times'], function (bound, discipline_id) {

        var discipline = Discipline.fromId(discipline_id);

        var week_days = _.map(bound['days'], function (day_id) {
            return WeekDay.fromId(day_id);
        });

        var times = _.map(bound['times'], function (time_id) {
            return DayTime.fromId(time_id);
        });

        return new DisciplineTimesBound(discipline, week_days, times);
    });
};

function DisciplineAuditoriesBound(discipline, auditories) {
    this.discipline = discipline || new Discipline();
    this.auditories = auditories || [ new Auditory() ];
}

DisciplineAuditoriesBound.fromSchedulerConfig = function (config) {
    if (typeof (config) !== 'object')
        return [ new DisciplineAuditoriesBound() ];

    if (typeof (config['bounds']) === 'undefined' ||
        typeof (config['bounds']['discipline_auditories']) === 'undefined') {
        return [ new DisciplineAuditoriesBound() ];
    }

    return _.map(config['bounds']['discipline_auditories'], function (bound, discipline_id) {

        var discipline = Discipline.fromId(discipline_id);

        var auditories = _.map(bound['values'], function (auditory_id) {
            return Auditory.fromId(auditory_id);
        });

        return new DisciplineAuditoriesBound(discipline, auditories);
    });
};