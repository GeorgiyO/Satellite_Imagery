export function getErrorPage(error) {
    return
`
<main>
    <section>
        <h2>Упс!</h2>
        <p>
            Ошибка: ${error}
        </p>
        <a class="ajaxLink" href="/">
            Вернуться на главную страницу
        </a>
    </section>
</main>
`
}