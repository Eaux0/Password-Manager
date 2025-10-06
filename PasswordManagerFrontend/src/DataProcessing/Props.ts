export interface HomeProps {
  token: string | null;
  setToken: (token: string | null) => void;
  setLoggedInStatus: (status: boolean) => void;
  sessionId: number | null;
}

export interface MessageBanner {
  title: string;
  description: string | null;
  type: "ERROR" | "INFO";
}

export interface BannerMessageViewProps{
    showErrorOrInfo: MessageBanner;
    setShowErrorOrInfo: (showErrorOrInfo : MessageBanner) => void;
}

export interface GridViewProps {
  setAddPasswordModalShow: (show: boolean) => void;
  sessionId: number | null;
}

export interface LineViewProps {
  index: number | null;
  gridTitle: string | null;
  gridDescription: string | undefined;
  setSelectedGrid?: (index: number | null) => void;
  setAddPasswordModalShow: (show: boolean) => void;
  sessionId: number | null;
}

export interface ListItem {
  id: number;
  title: string;
  description: string | undefined;
}

export interface LoginViewProps {
  username: string;
  setUsername: (username: string) => void;
  password: string;
  setPassword: (password: string) => void;
  handleSubmitOnSignIn: (e: React.FormEvent<HTMLFormElement>) => void;
  setSignUpMode: (mode: boolean) => void;
}

export interface ModalViewTemplateProps {
  index: number | null;
  modalType: string;
  title?: string;
  description?: string;
  username?: string;
  password?: string;
  editMode?: boolean;
  showPassword?: boolean;
  setSelectedPassword?: (index: number | null) => void;
  setEditMode?: (editMode: boolean) => void;
  setShowPassword?: (show: boolean) => void;
  setUsername?: (username: string) => void;
  setPassword?: (password: string) => void;
  copyToClipboard?: (value: string, field: "username" | "password") => void;
  copiedField?: "username" | "password" | null;
  handleInput?: (
    e: React.FormEvent<HTMLHeadingElement | HTMLParagraphElement>,
    type: "title" | "description"
  ) => void;
  setAddPasswordModalShow?: (show: boolean) => void;
  setAddGroupModalShow?: (show: boolean) => void;
  sessionId: number | null;
}

export interface SignUpViewProps {
  username: string;
  setUsername: (username: string) => void;
  password: string;
  setPassword: (password: string) => void;
  handleSubmitOnSignUp: (e: React.FormEvent<HTMLFormElement>) => void;
}

export interface LoginProps {
  setToken: (token: string) => void;
  setSessionId: (sessionId: number) => void;
}

export interface AddGroupHolderTemplateProps {
  buttonStyle?: (color: "gray" | "blue" | "red") => React.CSSProperties;
  setAddGroupModalShow?: (show: boolean) => void;
  sessionId: number | null;
}

export interface AddPasswordHoldertemplateProps {
  buttonStyle?: (color: "gray" | "blue" | "red") => React.CSSProperties;
  setAddPasswordModalShow?: (show: boolean) => void;
  sessionId: number | null;
}

export interface GridHolderTemplateProps {
  index: number;
  title: string;
  description: string | undefined;
  setSelectedGrid: (index: number | null) => void;
  sessionId: number | null;
}

export interface ListHolderTemplateProps {
  index: number;
  title: string;
  description: string | undefined;
  sessionId: number | null;
}

export interface PasswordHolderTemplateProps {
  index: number | null;
  title?: string;
  description?: string;
  username?: string;
  password?: string;
  editMode?: boolean;
  showPassword?: boolean;
  setSelectedPassword?: (index: number | null) => void;
  setEditMode?: (editMode: boolean) => void;
  setShowPassword?: (show: boolean) => void;
  setUsername?: (username: string) => void;
  setPassword?: (password: string) => void;
  copyToClipboard?: (value: string, field: "username" | "password") => void;
  copiedField?: "username" | "password" | null;
  handleInput?: (
    e: React.FormEvent<HTMLHeadingElement | HTMLParagraphElement>,
    type: "title" | "description"
  ) => void;
  buttonStyle?: (color: "gray" | "blue" | "red") => React.CSSProperties;
  sessionId: number | null;
}