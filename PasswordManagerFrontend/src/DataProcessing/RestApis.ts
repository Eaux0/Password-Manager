
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


const encodedInterface = (obj: any): string => {
  return obj.toString()
}

const decodedInterface = (str: string): any => {
  return JSON.parse(str);
};


async function encryptWithPublicKey(
  publicKeyPem: string,
  plaintext: string
): Promise<string> {

  // Convert PEM public key to ArrayBuffer
  function pemToArrayBuffer(pem: string): ArrayBuffer {
    const b64 = pem;
    const binary = atob(b64);
    const buffer = new ArrayBuffer(binary.length);
    const view = new Uint8Array(buffer);
    for (let i = 0; i < binary.length; i++) {
      view[i] = binary.charCodeAt(i);
    }
    return buffer;
  }

  // Convert ArrayBuffer to Base64
  function arrayBufferToBase64(buffer: ArrayBuffer): string {
    const bytes = new Uint8Array(buffer);
    let binary = '';
    for (let i = 0; i < bytes.byteLength; i++) {
      binary += String.fromCharCode(bytes[i]);
    }
    return btoa(binary);
  }

  // Import public key (SPKI format)
  const publicKey = await crypto.subtle.importKey(
    'spki',
    pemToArrayBuffer(publicKeyPem),
    {
      name: 'RSA-OAEP',
      hash: 'SHA-256',
    },
    false,
    ['encrypt']
  );

  // Encode plaintext to Uint8Array
  const encoder = new TextEncoder();
  const data = encoder.encode(plaintext);

  // Encrypt data
  const encrypted = await crypto.subtle.encrypt(
    {
      name: 'RSA-OAEP',
    },
    publicKey,
    data
  );

  // Return Base64 ciphertext
  return arrayBufferToBase64(encrypted);
}


console.log(decodedInterface(encodedInterface({ groupId: 1, userId: 1, groupName: "Group 1" })));
console.log(encryptWithPublicKey("a","a"));