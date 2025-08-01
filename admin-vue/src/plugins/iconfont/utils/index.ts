export function createLink(url: string, id?: string) {
  const link = document.createElement('link')
  link.href = url
  link.type = 'text/css'
  link.rel = 'stylesheet'
  if (id) {
    link.id = id
  }

  setTimeout(() => {
    document.getElementsByTagName('head').item(0)?.appendChild(link)
  }, 0)
}
