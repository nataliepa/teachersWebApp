$(document).ready(function(){
    const updateModal = document.getElementById('updateModal');
    if (updateModal) {
        updateModal.addEventListener('show.bs.modal', event => {
            // Button that triggered the modal
            const button = event.relatedTarget;
            // Extract info from data-bs-* attributes
            const id = button.getAttribute('data-bs-id');
            const sname = button.getAttribute('data-bs-sname');
            const fname = button.getAttribute('data-bs-fname');

            // Update the modal's content.
            const modalBodyInputId = updateModal.querySelector('.modal-body #id');
            const modalBodyInputOldSname = updateModal.querySelector('.modal-body #oldSname');
            const modalBodyInputNewSname = updateModal.querySelector('.modal-body #newSname');
            const modalBodyInputOldFname = updateModal.querySelector('.modal-body #oldFname');
            const modalBodyInputNewFname = updateModal.querySelector('.modal-body #newFname');

            modalBodyInputId.value = id;
            modalBodyInputOldSname.value = sname;
            modalBodyInputNewSname.value = sname;
            modalBodyInputOldFname.value = fname;
            modalBodyInputNewFname.value = fname;
        });
    }

    $("#updateForm").submit(function(e) {
        e.preventDefault();
        if (confirm("Είστε σίγουροι για τις αλλαγές που θέλετε να κάνετε;")) {
            $(this).unbind('submit').submit();
        }
    });

    $(".delete-form").submit(function(e) {
        e.preventDefault();
        if (confirm("Είστε σίγουροι ότι θέλετε να διαγράψτε τον/την διδάσκοντα/ουσα;")) {
            $(this).unbind('submit').submit();
        }
    });


    var url_string = window.location.href;
    var url = new URL(url_string);
    var paramValue = url.searchParams.get("searchTerm");
    if(paramValue !== null) {
        $("ul#pagination-list a").each(function (){
            let href = $(this).attr('href');

            $(this).attr('href', href + '&searchTerm=' + paramValue);
        });

        $("ul#pagination-menu a").each(function (){
            let href = $(this).attr('href');

            $(this).attr('href', href + '&searchTerm=' + paramValue);
        });
    }

    $('#teacherSurname').on('input',function(){
        let input = this.value;
        let searchButton = $('#search-button');
        let href = searchButton.attr('href');
        if(href.indexOf('searchTerm') === -1) {
            searchButton.attr('href', href + '&searchTerm=' + input);
        } else {
            href = href.replace(/(searchTerm=)[^\&]+/, '$1' + input);
            searchButton.attr('href', href);
        }
    });

    $('input#teacherSurname').on('keypress', function (e) {
        if(e.key === 'Enter') {
            e.preventDefault();
            $('a#search-button')[0].click();
        }
    });

});


