import { useDictStore } from './dict'
import { useMealStore } from './meal'
import { useOrderStore } from './order'
import { useUserStore } from './user'

export function useStore() {
  return {
    user: useUserStore(),
    dict: useDictStore(),
    meal: useMealStore(),
    order: useOrderStore(),
  }
}
