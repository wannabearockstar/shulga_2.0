function GroupInfo(group, curriculum) {
    this.group = group || new Group();
    this.curriculum = curriculum || [ new GroupCurriculum() ];
}

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
    if (typeof (id) !== 'number')
        return new Group();

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
    if (typeof (id) !== 'number')
        return new Professor();

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
    if (typeof (id) !== 'number')
        return new Discipline();

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
    if (typeof (id) !== 'number')
        return new LessonType();

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
    if (typeof (id) !== 'number')
        return new Auditory();

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

function Status(finished, progress, fitness, maxFitness) {
    this.finished = finished || false;
    this.progress = progress || 0.0;
    this.fitness = fitness || 0.0;
    this.maxFitness = maxFitness || false;
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

function AuditoryTimesBound(auditory, days, times) {
    this.auditory = auditory || new Auditory();
    this.days = days || [ new WeekDay() ];
    this.times = times || [ new DayTime() ];
}

function DisciplineTimesBound(discipline, days, times) {
    this.discipline = discipline || new Discipline();
    this.days = days || [ new WeekDay() ];
    this.times = times || [ new DayTime() ];
}

function DisciplineAuditoriesBound(discipline, auditories) {
    this.discipline = discipline || new Discipline();
    this.auditories = auditories || [ new Auditory() ];
}