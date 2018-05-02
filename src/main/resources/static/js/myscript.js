$(document).ready(function () {
    // csrf_token
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content")


    // load initial data
    getDishes();
    getCategories();

    //POST (save) DISH
    $('#new-dish').submit((e)=> {
        e.preventDefault();
        addDish()
    });

    function addDish() {
        let formData = {
            title: $('#title-input').val(),
            price: $('#price-input').val(),
            category: $('#category-input').val()

        };
        if (formData.title.trim() === '' ||
            formData.price.trim() === '' ||
            formData.category.trim() === '') {
            $('#dish-form-alert').removeClass('hidden');
        } else {
            $.ajax({
                type: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                url: "/admin/add_dish",
                data: JSON.stringify(formData),
                success: function (data) {
                    console.log("response  "+data);
                    getDishes();
                    $('#title-input').val('');
                    $('#price-input').val('');
                    $('#category-input').val('')
                }, error: function () {
                    console.log("ERROR request")
                }
            })
        }
    }

    function getDishes() {
        $.getJSON('/admin/get_dishes', function (data) {
            $('#dish-form-alert').addClass('hidden');

            $("ul#dish-list > li").remove();
            $.each(data, function (key, value) {
                let id = value['id'];
                let title = value['title'];
                $("#dish-list").prepend(
                    '<li value="test" id="'+id+'">' + title +
                    '<button id="remove-dish" '+
                    'class="btn btn-link update-item-li">Удалить</button>'+'</li>');
            });
        });
    }

    // DELETE DISH
    $('#dish-list').on('click', '#remove-dish', function () {
        let id = $(this).parents().attr('id');
        console.log("/admin/remove_dish/"+id);
        $.ajax({
                    type: "DELETE",
                    beforeSend: function(xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    url: "/admin/remove_dish/"+id,
                    success: function () {
                        console.log("response  success");
                        getDishes();
                    }, error: function (error) {
                        console.log("ERROR request "+error)
                    }
                })
    })




    // CATEGORIES

    $('#new-category').submit((e)=> {
        e.preventDefault();
        addCategory()
    });

    function addCategory() {
        let formData = {
            title: $('#title-category').val(),
        };
        if (formData.title.trim() === '') {
            $('#category-form-alert').removeClass('hidden');
        } else {
            $.ajax({
                type: "POST",
                headers: {
                    'Content-Type': 'application/json'
                },
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                url: "/admin/add_category",
                data: JSON.stringify(formData),
                success: function (data) {
                    console.log("response  "+data);
                    getCategories();
                    $('#title-category').val('')
                }, error: function () {
                    console.log("ERROR request")
                }
            })
        }

    }

    function getCategories() {
        $.getJSON('/admin/get_categories', function (data) {
            $('#category-form-alert').addClass('hidden');

            $("ul#category-list > li").remove();

            $.each(data, function (key, value) {
                let id = value['id'];
                let title = value['title'];
                $("#category-list").prepend(
                    '<li value="test" id="'+id+'">' + title +
                    '<button id="remove-dish" '+
                    'class="btn btn-link update-item-li">Удалить</button>'+'</li>');
            });
        });
    }

    $('#category-list').on('click', '#remove-dish', function () {
        let id = $(this).parents().attr('id');
        $.ajax({
            type: "DELETE",
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            url: "/admin/remove_category/"+id,
            success: function () {
                console.log("response  success");
                getCategories()
            }, error: function (error) {
                console.log("ERROR request "+error)
            }
        })
    })

});

