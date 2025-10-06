import type { BannerMessageViewProps } from "../DataProcessing/Props";

const BannerMessageView = ({
  showErrorOrInfo,
  setShowErrorOrInfo,
}: BannerMessageViewProps) => {
  console.log(showErrorOrInfo, setShowErrorOrInfo);
  return <div>BannerMessageView</div>;
};

export default BannerMessageView;
