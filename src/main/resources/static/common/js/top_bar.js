// 高亮显示当前菜单
$(document).ready(function () {
    $('#nav-1 li a').each(function () {
        const $this = $(this);
        if ($this[0].href === String(window.location)) {
            $this.addClass('active');
        }
    });
});
