import CryptoJS from 'crypto-js';


export interface GroupResponse {
  groupId: number;
  userId: number;
  groupName: string;
  groupDescription?: string;
}

export interface UserPasswordResponse {
  userPasswordId: number;
  userId: number;
  groupId: number;
  userPasswordUserName: string;
  userPassword: string;
}

export interface UserPasswordDetailResponse {
  userPasswordDetailId: number;
  userId: number;
  userPasswordId: number;
  userPasswordName: string;
  userPasswordDescription?: string;
}

let aesKey;

export function generateAesKey(): string {
  const key = crypto.getRandomValues(new Uint8Array(128));
  aesKey = btoa(String.fromCharCode(...key));
  return aesKey;
}

function deriveAESKey(hasher: string): CryptoJS.lib.WordArray {
  const sha256 = CryptoJS.SHA256(hasher);
  return CryptoJS.lib.WordArray.create(sha256.words.slice(0, 4), 16); 
}

export function encryptAES(plainText: string, hasher: string): string {
  const key = deriveAESKey(hasher);
  const encrypted = CryptoJS.AES.encrypt(plainText, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7,
  });

  return encrypted.toString(); 
}

export function decryptAES(base64CipherText: string, hasher: string): string {
  const key = deriveAESKey(hasher);
  const decrypted = CryptoJS.AES.decrypt(base64CipherText, key, {
    mode: CryptoJS.mode.ECB,
    padding: CryptoJS.pad.Pkcs7,
  });

  return decrypted.toString(CryptoJS.enc.Utf8);
}