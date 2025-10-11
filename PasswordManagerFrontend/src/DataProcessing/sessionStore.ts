import { create } from 'zustand';

interface SessionStore {
  sessionId: number | null;
  setSessionId: (id: number | null) => void;
}

const useSessionStore = create<SessionStore>((set) => ({
  sessionId: null,
  setSessionId: (id) => set({ sessionId: id }),
}));

export default useSessionStore;