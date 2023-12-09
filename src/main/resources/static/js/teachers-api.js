function getAllRecords() {
    var $searchResultsDiv = $('.search-results');
    var $spinner = $('.tr-spinner');

    $spinner.show(); // Show the spinner while loading

    $.get('/api/teachers')
        .done(function(data) {
            $spinner.hide(); // Hide the spinner when results are loaded

            if(!data._embedded) {
                $searchResultsDiv.html('<p>Δεν βρέθηκαν εγγραφές.</p>');
                return;
            }

            // Assuming the data is an array of teacher objects
            var teachers = data._embedded.teacherList;

            var tableHtml = '';

            $.each(teachers, function(index, teacher) {
                tableHtml += '<tr>';
                tableHtml += '<td>' + teacher.id + '</td>';
                tableHtml += '<td>' + teacher.sname + '</td>';
                tableHtml += '<td>' + teacher.fname + '</td>';
                tableHtml += '<td><span class="bi bi-pencil-square" data-bs-toggle="modal" data-bs-target="#updateModal" data-bs-id="' + teacher.id + '" data-bs-sname="' + teacher.sname + '" data-bs-fname="' + teacher.fname + '"></span></td>';
                tableHtml += '<td><span class="bi bi-trash" data-id="'+ teacher.id +'"></span></td>';
                tableHtml += '</tr>';
            });

            $searchResultsDiv.html(tableHtml);
            assignDeleteTeacherEvents();
            assignUpdateTeacherEvents();
        })
        .fail(function() {
            $spinner.hide(); // Hide the spinner on error
            $searchResultsDiv.html('<p>Error loading search results.</p>');
        });
}

function insertTeacherReq(id, sname, fname) {
    var insert = {}
    insert["id"] = id;
    insert["sname"] = sname;
    insert["fname"] = fname;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/teachers",
        data: JSON.stringify(insert),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function(data) {
            $('#modal-insert-close')[0].click();
            $("#success-alert").html('<i className="bi bi-check"></i> O διδάσκων προστέθηκε επιτυχώς');
            $("#success-alert").show();
            $('#search').val("");
            getAllRecords();
            setTimeout(function() {
                $("#success-alert").hide();
            }, 4000);
        },
        error: function (e) {
            $("#input-id").addClass("id-error");
        }
    });
}

function searchTeacherReq() {
    var $searchResultsDiv = $('.search-results');
    let searchTerm = $("#search").val();

    if(searchTerm === "") {
        getAllRecords();
        return;
    }

    $.get('/api/teachers?searchTerm=' + searchTerm)
        .done(function(data) {

            if(!data._embedded) {
                $searchResultsDiv.html('<p>Δεν βρέθηκαν εγγραφές.</p>');
                return;
            }
            // Assuming the data is an array of teacher objects
            var teachers = data._embedded.teacherList;

            var tableHtml = '';

            $.each(teachers, function(index, teacher) {
                tableHtml += '<tr>';
                tableHtml += '<td>' + teacher.id + '</td>';
                tableHtml += '<td>' + teacher.sname + '</td>';
                tableHtml += '<td>' + teacher.fname + '</td>';
                tableHtml += '<td><span class="bi bi-pencil-square" data-bs-toggle="modal" data-bs-target="#updateModal" data-bs-id="' + teacher.id + '" data-bs-sname="' + teacher.sname + '" data-bs-fname="' + teacher.fname + '"></span></td>';
                tableHtml += '<td><span class="bi bi-trash" data-id="'+ teacher.id +'"></span></td>';
                tableHtml += '</tr>';
            });

            $searchResultsDiv.html(tableHtml);
            assignDeleteTeacherEvents();
            assignUpdateTeacherEvents();
        })
        .fail(function() {
            $searchResultsDiv.html('<p>Error loading search results.</p>');
        });
}

function assignSearchTeacherEvent() {

    $("#search").on("input", function() {
        searchTeacherReq();
    });
}

function updateTeacherReq(id, sname, fname) {
    var update = {}
    update["id"] = id;
    update["sname"] = sname;
    update["fname"] = fname;

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: "/api/teachers/" + id,
        data: JSON.stringify(update),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function(data) {
            $('#modal-update-close')[0].click();
            $("#success-alert").html('<i className="bi bi-check"></i> O διδάσκων ενημερώθηκε επιτυχώς');
            $("#success-alert").show();
            searchTeacherReq();
            setTimeout(function() {
                $("#success-alert").hide();
            }, 4000);
        },
        error: function (e) {
        }
    });
}

function deleteTeacherReq(id) {
    $.ajax({
        type: "DELETE",
        url: "/api/teachers/"+id,
        cache: false,
        timeout: 600000,
        success: function() {
            $("#success-alert").html('<i className="bi bi-check"></i> O διδάσκων διαγράφηκε επιτυχώς');
            $("#success-alert").show();
            searchTeacherReq();
            setTimeout(function() {
                $("#success-alert").hide();
            }, 4000);
        },
        error: function (e) {

        }
    });
}

function assignInsertTeacherEvent() {

    $('#modal-insert-submit').on("click", function() {
        let id = $("#id").val();
        let sname = $("#sname").val();
        let fname = $("#fname").val();

        if(checkInsertFormForErrors(id, sname, fname)) {
            return;
        }

        insertTeacherReq(id, sname, fname);
    });
}

function assignDeleteTeacherEvents() {

    $('span.bi.bi-trash').on("click", function() {
        let id = $(this).attr('data-id');
        deleteTeacherReq(id);
    });
}

function assignUpdateTeacherEvents() {

    $('#modal-update-submit').on("click", function() {
        let id = $('#update-id').val();
        let sname = $('#update-sname').val();
        let fname = $('#update-fname').val();

        updateTeacherReq(id, sname, fname);
    });
}

function checkInsertFormForErrors(id, sname, fname) {
    let error = false;

    if(id === "") {
        $("#input-id").addClass("error");
        error = true;
    }
    if(sname === "") {
        $("#input-sname").addClass("error");
        error = true;
    }
    if(fname === "") {
        $("#input-fname").addClass("error");
        error = true;
    }
    return error;
}

// Example usage
$(document).ready(function() {
    getAllRecords();
    assignInsertTeacherEvent();
    assignSearchTeacherEvent();
    $("#success-alert").hide();
    $("#id").on("input", function() {
        if($(this).val() !== "") {
            $('#input-id').removeClass("error");
        }
        $('#input-id').removeClass("id-error");
    });

    $("#sname").on("input", function() {
        if($(this).val() !== "") {
            $('#input-sname').removeClass("error");
        }
    });

    $("#fname").on("input", function() {
        if($(this).val() !== "") {
            $('#input-fname').removeClass("error");
        }
    });

    $("#insertModal").on("hide.bs.modal", function() {
        $("#insertForm")[0].reset();
        $('#input-id').removeClass("error");
        $('#input-id').removeClass("id-error");
        $('#input-sname').removeClass("error");
        $('#input-fname').removeClass("error");
    });

    $("#updateModal").on("show.bs.modal", function(e) {
        const button = e.relatedTarget;
        const id = button.getAttribute('data-bs-id');
        const sname = button.getAttribute('data-bs-sname');
        const fname = button.getAttribute('data-bs-fname');

        $('#update-id').val(id);
        $('#update-sname').val(sname);
        $('#update-fname').val(fname);
    });
});