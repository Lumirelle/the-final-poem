import { useDictStore } from './dict'
import { useUserStore } from './user'

export function useStore() {
  return {
    user: useUserStore(),
    dict: useDictStore(),
  }
}
